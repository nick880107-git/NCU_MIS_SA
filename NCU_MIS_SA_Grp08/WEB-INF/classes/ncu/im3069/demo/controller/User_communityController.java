package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Inbox;
import ncu.im3069.demo.app.InboxHelper;
import ncu.im3069.demo.app.User_community;
import ncu.im3069.demo.app.User_communityHelper;


import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/user_community.do")
public class User_communityController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    
    private User_communityHelper uch =  User_communityHelper.getHelper();
    private InboxHelper inboxHelper = InboxHelper.getHelper();


    public User_communityController() {
        super();
    }

    /**
     * 處理 Http Method 請求 GET 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        String id = jsr.getParameter("community_id");
        String member=jsr.getParameter("member");
        String user_id = jsr.getParameter("user_id");
        JSONObject resp = new JSONObject();
        
        /** 取出經解析到 JsonReader 之 Request 參數 */
        if(id.isEmpty()) {
        	
        	JSONObject query = uch.getAllcommunity(user_id);
        	resp.put("status", "200");
            resp.put("message", "取得會員所屬社群資料成功");
            resp.put("response", query);
        }
        else {
        	
        	User_community uc=new User_community(Integer.parseInt(user_id),Integer.parseInt(id));
        	
            if(uch.hasAccess_Host(uc)) {
            	
            	if(!member.isEmpty()) {
                	JSONObject query = uch.getByID(id);
                	resp.put("status", "200");
                    resp.put("message", "資料取得成功");
                    resp.put("response", query);
                }
                else {
                	JSONObject query = uch.getOthers(id);
                	resp.put("status", "200");
                    resp.put("message", "資料取得成功");
                    resp.put("response", query);
                }
            	resp.put("isHost", true);
            }
            else {
            	
            	resp.put("isHost", false);
            }
        }
        jsr.response(resp, response);
	}

    /**
     * 處理 Http Method 請求 POST 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到 JSONObject 之 Request 參數 */
        int user_id = jso.getInt("user_id");
        int community_id=jso.getInt("community_id");
        JSONObject resp = new JSONObject();
        /** 建立一個新的社群物件 */
        User_community uc = new User_community(user_id,community_id);
        if(uch.checkDuplicate(uc)==false) {
        	uch.create(uc);
        	 //創建社群，並將該會員設定為該社群的管理者
            if(jso.has("isHost")) { 
            	JSONObject state =uch.update_host(uc);
            	
            		resp.put("status", "200");
                    resp.put("message", "管理員設定成功！");
                    resp.put("response",state);
            }
            if(jso.has("isHostApproved")) {
            	Inbox i =new Inbox(user_id);
            	i.setCommunity(community_id);
            	JSONObject query =inboxHelper.create(i);
            	System.out.println(query);
            	JSONObject state =uch.hostApproved(uc);
            	
            		resp.put("status", "200");
                    resp.put("message", "邀請成功！");
                    resp.put("response",state);
            	
            }
            
            // 加入社群，等待該社群管理員審核
            if(jso.has("isMemberApproved")) {
            	
            	JSONObject state =uch.memberApproved(uc);
            	
            		resp.put("status", "200");
                    resp.put("message", "申請加入成功！");
                    resp.put("response",state);
            	
            	
            }

            /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
            jsr.response(resp, response);
        }
        else {
            /** 以字串組出JSON格式之資料 */
            String resp1 = "{\"status\": \'400\', \"message\": \'User_community資料重複！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp1, response);
        }
	}
	
	 /** 處理Http Method請求DELETE方法（刪除） */
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		        JsonReader jsr = new JsonReader(request);
		        JSONObject jso = jsr.getObject();
		        
		        /** 取出經解析到JSONObject之Request參數 */
		        int id = jso.getInt("user_community_id");
		        
		        
		        /** 透過CommunityHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
		        JSONObject query = uch.deleteByID(id);
		        
		        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
		        JSONObject resp = new JSONObject();
		        resp.put("status", "200");
		        resp.put("message", "移除成功！");
		        resp.put("response", query);

		        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
		        jsr.response(resp, response);
		    }

	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到 JSONObject 之 Request 參數 */
        int operator_id=jso.getInt("operator_id");
        int user_id = jso.getInt("user_id");
        int community_id=jso.getInt("community_id");
        /** 建立一個新的社群物件 */
        User_community uc = new User_community(user_id,community_id);
        User_community operator = new User_community(operator_id,community_id);
        /** 透過 CommentHelper 物件的 create() 方法新建一筆訂單至資料庫 */
        JSONObject resp = new JSONObject();
      //先確認操作者是否為管理員，如果是則將目標會員升級成管理員
        if(jso.has("isHost")&&uch.hasAccess_Host(operator)) { 
        	JSONObject state =uch.update_host(uc);
        	
        		resp.put("status", "200");
                resp.put("message", "管理員設定成功！");
                resp.put("response",state);
        	
        	
        }
        //先確認操作者是否為管理員，如果是則將目標會員變成社員
        if(jso.has("isHostApproved")&&uch.hasAccess_Host(operator)) {
        	JSONObject state =uch.hostApproved(uc);
        	
        		resp.put("status", "200");
                resp.put("message", "管理員認證成功！");
                resp.put("response",state);
        	
        	
        }
        //普通會員同意社群邀請
        if(jso.has("isMemberApproved")) {
        	
        	JSONObject state =uch.memberApproved(uc);
        	
        		resp.put("status", "200");
                resp.put("message", "申請加入成功！");
                resp.put("response",state);
        
        }
        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}

}
