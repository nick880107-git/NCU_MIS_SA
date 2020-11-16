package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.User;
import ncu.im3069.demo.app.UserHelper;
import ncu.im3069.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class UserController<br>
 * UserController類別（class）主要用於處理User相關之Http請求（Request），繼承HttpServlet
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
@WebServlet("/api/user.do")
public class UserController extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** uh，UserHelper之物件與User相關之資料庫方法（Sigleton） */
    private UserHelper uh =  UserHelper.getHelper();

    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到JSONObject之Request參數 */
        String email = jso.getString("email");
        String hashed_password = jso.getString("hashed_password");
        String phone_number=jso.getString("phone_number");
        String name = jso.getString("name");
        int age=jso.getInt("age");
        String birthday=jso.getString("birthday");
        boolean gender=jso.getBoolean("gender");

        /** 建立一個新的會員物件 */
        User u  = new User(email, hashed_password, phone_number,name,age,birthday,gender);

        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(email.isEmpty() ||age<=0 || hashed_password.isEmpty() || name.isEmpty()||phone_number.isEmpty()|| birthday.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'亲，欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過UserHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
        else if (uh.checkDuplicate(u)==false) {
            /** 透過UserHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = uh.create(u);

            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 註冊會員資料...");
            resp.put("response", data);

            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
    }

    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        
        String user_id = jsr.getParameter("user_id");
        String email=jsr.getParameter("email");
        String password=jsr.getParameter("hashed_password");
        String isRand=jsr.getParameter("isRand");
        /** 判斷該字串是否存在，若存在代表要取回個別會員之資料，否則代表要取回全部資料庫內會員之資料 */
        if (user_id.isEmpty()) {
        		if(uh.checkAccount(email,password)==true) {
        			JSONObject query = uh.getByEmail(email);
                    /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                    JSONObject resp = new JSONObject();
                    resp.put("status", "200");
                    resp.put("message", "登入成功");
                    resp.put("response", query);
                    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                    jsr.response(resp, response);
        		}
        		else {
        			String resp = "{\"status\": \'400\', \"message\": \'帳號或密碼錯誤\', \'response\': \'\'}";
                    /** 透過JsonReader物件回傳到前端（以字串方式） */
                    jsr.response(resp, response);
        		}
        }
        else {
        	if(!isRand.isEmpty()) {
        		/** 透過UserHelper物件之getAll()方法取回所有會員之資料，回傳之資料為JSONObject物件 */
                JSONObject query = uh.getAll(user_id);

                /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                JSONObject resp = new JSONObject();
                resp.put("status", "200");
                resp.put("message", "隨機會員資料取得成功");
                resp.put("response", query);

                /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                jsr.response(resp, response);
        	}
        	else {
        		/** 透過UserHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
                JSONObject query = uh.getByID(user_id);

                /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                JSONObject resp = new JSONObject();
                resp.put("status", "200");
                resp.put("message", "亲，會員資料取得成功");
                resp.put("response", query);

                /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                jsr.response(resp, response);
        	}
            
        }
    }

    /**
     * 處理Http Method請求DELETE方法（刪除）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到JSONObject之Request參數 */
        int user_id = jso.getInt("user_id");

        /** 透過UserHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
        JSONObject query = uh.deleteByID(user_id);

        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "亲，會員移除成功！");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

    /**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        int user_id=jso.getInt("user_id");
        
        	/** 取出經解析到JSONObject之Request參數 */
            String email = jso.getString("email");
            String hashed_password = jso.getString("hashed_password");
            String phone_number=jso.getString("phone_number");
            String name = jso.getString("name");
            String work=jso.getString("work");
            String interest=jso.getString("interest");
            /** 透過傳入之參數，新建一個以這些參數之會員User物件 */
            User u = new User(user_id, hashed_password,email,phone_number,name,work,interest);

            /** 透過User物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
            JSONObject data = uh.update(u);

            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "亲，成功更新會員資料...");
            resp.put("response", data);

            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        
        
    }

