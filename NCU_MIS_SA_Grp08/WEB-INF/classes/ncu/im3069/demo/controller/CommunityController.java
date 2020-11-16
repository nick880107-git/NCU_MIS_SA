package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Community;
import ncu.im3069.demo.app.CommunityHelper;

import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/community.do")
public class CommunityController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** ch，ProductHelper 之物件與 Product 相關之資料庫方法（Singleton） */
    private CommunityHelper ch =  CommunityHelper.getHelper();



    public CommunityController() {
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
        String id = jsr.getParameter("comment_id");

        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();

        /** 判斷該字串是否存在，若存在代表要取回單一社群之資料，否則代表要取回全部資料庫內社群之資料 */
        if (!id.isEmpty()) {
          /** 透過 CommunityHelper 物件的 getByID() 方法自資料庫取回該筆社群之資料，回傳之資料為 JSONObject 物件 */
          JSONObject query = ch.getByID(id);
          resp.put("status", "200");
          resp.put("message", "單筆社群資料取得成功");
          resp.put("response", query);
        }
        else {
          /** 透過 CommunityHelper 物件之 getAll() 方法取回所有訂單之資料，回傳之資料為 JSONObject 物件 */
          JSONObject query = ch.getAll();
          resp.put("status", "200");
          resp.put("message", "所有社群資料取得成功");
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
        String name = jso.getString("name");
        String content = jso.getString("content");
        /** 建立一個新的社群物件 */
        Community c = new Community(name,content);

        /** 透過 CommunityHelper 物件的 create() 方法新建一筆訂單至資料庫 */
        JSONObject query=ch.create(c);
        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "社群創建成功！");
        resp.put("response", query);

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}
	
	/** 處理Http Method請求PUT方法（更新）*/
	 public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		        JsonReader jsr = new JsonReader(request);
		        JSONObject jso = jsr.getObject();
		        
		        /** 取出經解析到JSONObject之Request參數 */
		        int id = jso.getInt("community_id");
		        String name = jso.getString("name");
		        String content = jso.getString("content");
		        
		        

		        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
		        Community c = new Community(id, name,content);
		        
		        /** 透過Community物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
		        JSONObject data = ch.update(c);
		        
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
		        int id = jso.getInt("community_id");
		        
		        /** 透過CommunityHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
		        JSONObject query = ch.deleteByID(id);
		        
		        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
		        JSONObject resp = new JSONObject();
		        resp.put("status", "200");
		        resp.put("message", "社群移除成功！");
		        resp.put("response", query);

		        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
		        jsr.response(resp, response);
		    }


}
