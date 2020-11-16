package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;


import ncu.im3069.demo.app.MessageHelper;
import ncu.im3069.demo.app.Message;

import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/message.do")
public class MessageController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    
    private MessageHelper messageHelper =  MessageHelper.getHelper();



    public MessageController() {
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
        String id = jsr.getParameter("match_id");

        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();

        /** 判斷該字串是否存在，若存在代表要取回單一社群之資料，否則代表要取回全部資料庫內社群之資料 */
        
          /** 透過 CommunityHelper 物件的 getByID() 方法自資料庫取回該筆社群之資料，回傳之資料為 JSONObject 物件 */
          JSONObject query = messageHelper.getByID(id);
          resp.put("status", "200");
          resp.put("message", "聊天室訊息取得成功");
          resp.put("response", query);
       
        

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
        int match_id = jso.getInt("match_id");
        int user_id=jso.getInt("user_id");
        String message_content = jso.getString("message_content");
        /** 建立一個新的社群物件 */
        Message m = new Message(match_id,user_id,message_content);

        /** 透過 CommentHelper 物件的 create() 方法新建一筆訂單至資料庫 */
        messageHelper.create(m);
        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "新增成功！");
        resp.put("response", m.getData());

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}
	


	 /** 處理Http Method請求DELETE方法（刪除） */
	 public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		        JsonReader jsr = new JsonReader(request);
		        JSONObject jso=jsr.getObject();
		        
		        if(jso.has("match_id")) {
		        	int jso_macth = jso.getInt("match_id");
			        
		        	JSONObject query=messageHelper.deleteAll(jso_macth);
		        	JSONObject resp = new JSONObject();
				    resp.put("status", "200");
				    resp.put("message", "聊天室刪除成功！");
				    resp.put("response", query);
				    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
				    jsr.response(resp, response);
		        }
		        if(jso.has("message_id")) {
		        	int jso_message=jso.getInt("message_id");
		        	JSONObject query=messageHelper.deleteByID(jso_message);
		        	JSONObject resp = new JSONObject();
				    resp.put("status", "200");
				    resp.put("message", "訊息移除成功！");
				    resp.put("response", query);
				    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
				    jsr.response(resp, response);
		        }
		      
		    }


}
