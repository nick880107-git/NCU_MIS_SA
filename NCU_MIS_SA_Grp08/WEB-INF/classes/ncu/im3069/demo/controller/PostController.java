package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;


import ncu.im3069.demo.app.Post;
import ncu.im3069.demo.app.PostHelper;
import ncu.im3069.demo.app.User_community;
import ncu.im3069.demo.app.User_communityHelper;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/post.do")
public class PostController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    private PostHelper ph=PostHelper.getHelper();
    private User_communityHelper uch=User_communityHelper.getHelper();


    public PostController() {
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
        String id = jsr.getParameter("community_id");
        String user_id=jsr.getParameter("user_id");
        User_community uc=new User_community(Integer.parseInt(user_id),Integer.parseInt(id));
        JSONObject resp = new JSONObject();
        if(uch.hasAccess(uc)) {
            /** 透過 CommunityHelper 物件的 getByID() 方法自資料庫取回該筆社群之資料，回傳之資料為 JSONObject 物件 */
            JSONObject query = ph.getByCommunityID(id);
            resp.put("status", "200");
            resp.put("message", "該社群貼文取得成功");
            resp.put("response", query);
        }
        else {
        	resp.put("status", "400");
        	resp.put("message", "您沒有權限查看貼文");
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
        int user_id=jso.getInt("user_id");
        int community_id=jso.getInt("community_id");
        
        String content = jso.getString("content");
        
        Post p = new Post(user_id,community_id,content);
        User_community uc=new User_community(user_id,community_id);
        
        JSONObject resp = new JSONObject();
        if(uch.hasAccess(uc)) {
        	JSONObject query = ph.create(p);
            resp.put("status", "200");
            resp.put("message", "貼文新增成功！");
            resp.put("response", query);
        }
        else {
        	resp.put("status", "400");
        	resp.put("message", "您沒有權限查看貼文");
        }
        
        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}
	
	/** 處理Http Method請求PUT方法（更新）*/
	 public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		        JsonReader jsr = new JsonReader(request);
		        JSONObject jso = jsr.getObject();
		        
		        /** 取出經解析到JSONObject之Request參數 */
		        int id = jso.getInt("post_id");
		        
		        String content = jso.getString("content");
		        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
		        Post p = new Post(id,content);
		        
		        /** 透過Community物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
		        JSONObject data = ph.update(p);
		        
		        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
		        JSONObject resp = new JSONObject();
		        resp.put("status", "200");
		        resp.put("message", "成功! 更新社群資料...");
		        resp.put("response", data);
		        
		        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
		        jsr.response(resp, response);
		    }

	 /** 處理Http Method請求DELETE方法（刪除） */
	 public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		        JsonReader jsr = new JsonReader(request);
		        JSONObject jso = jsr.getObject();
		        
		        /** 取出經解析到JSONObject之Request參數 */
		        int id = jso.getInt("post_id");
		        
		        /** 透過CommunityHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
		        JSONObject query = ph.deleteByID(id);
		        
		        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
		        JSONObject resp = new JSONObject();
		        resp.put("status", "200");
		        resp.put("message", "貼文移除成功！");
		        resp.put("response", query);

		        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
		        jsr.response(resp, response);
		    }


}
