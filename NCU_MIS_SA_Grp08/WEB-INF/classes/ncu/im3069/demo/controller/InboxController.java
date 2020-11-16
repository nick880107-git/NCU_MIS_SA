package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;


import ncu.im3069.demo.app.Inbox;
import ncu.im3069.demo.app.InboxHelper;


import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/inbox.do")
public class InboxController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    
    private InboxHelper inboxHelper =  InboxHelper.getHelper();



    public InboxController() {
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

        /** 取出經解析到 JsonReader 之 Request 參數 */
        String id = jsr.getParameter("user_id");
        String type=jsr.getParameter("type");
        System.out.println("user_id="+id);
        System.out.println("type="+type);
        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();

        if(type.equalsIgnoreCase("friend")) {
        	JSONObject query = inboxHelper.getFriend(id);
            resp.put("status", "200");
            resp.put("message", "通知取得成功");
            resp.put("response", query);
            System.out.println("query="+query);
        }
        if(type.equalsIgnoreCase("community")) {
        	JSONObject query = inboxHelper.getCommunity(id);
            resp.put("status", "200");
            resp.put("message", "通知取得成功");
            resp.put("response", query);
        }
        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
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
        Inbox i=new Inbox(user_id);
        if(jso.has("user_communtiy_id")) {
        	int user_community_id=jso.getInt("user_communtiy_id");
        	i.setCommunity(user_community_id);
        }
        if(jso.has("friend_match_id")) {
        	int friend_match_id=jso.getInt("friend_match_id");
        	i.setFriend(friend_match_id);
        }
        /** 建立一個新的社群物件 */
        

        /** 透過 CommentHelper 物件的 create() 方法新建一筆訂單至資料庫 */
        JSONObject query=inboxHelper.create(i);
        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "邀請寄送成功！");
        resp.put("response", query);

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}
	


	 /** 處理Http Method請求DELETE方法（刪除） */
	 public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		        JsonReader jsr = new JsonReader(request);
		        JSONObject jso = jsr.getObject();
		        
		        /** 取出經解析到JSONObject之Request參數 */
		        int id = jso.getInt("inbox_id");
		        
		        /** 透過CommunityHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
		        JSONObject query = inboxHelper.delete(id);
		        
		        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
		        JSONObject resp = new JSONObject();
		        resp.put("status", "200");
		        resp.put("message", "移除成功！");
		        resp.put("response", query);

		        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
		        jsr.response(resp, response);
		    }


}
