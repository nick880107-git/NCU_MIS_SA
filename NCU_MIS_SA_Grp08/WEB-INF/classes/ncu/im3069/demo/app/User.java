package ncu.im3069.demo.app;

import org.json.*;


// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class User
 * User類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class User {

    private int user_id;
    private String hashed_password;
    private String email;
    private String phone_number;
    private String name;
    private int age;
    private String birthday;
    private boolean gender;
    private String work = "None";
    private String interest = "None";
    


    /**
     * 實例化（Instantiates）一個新的（new）User物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立會員資料時，產生一名新的會員
     *
     * @param email 會員電子信箱
     * @param hashed_password 會員密碼
     * @param name
     _name 會員姓名
     */
    public User(String email,String hashed_password,String phone_number, String name, int age, String birthday, boolean gender) {
        this.email = email;
        this.hashed_password = hashed_password;
        this.phone_number = phone_number;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.gender = gender;
    }
    public User(int user_id,String hashed_password, String email, String phone_number, String name, String work, String interest) {
        this.user_id=user_id;
    	this.hashed_password = hashed_password;
        this.email = email;
        this.phone_number = phone_number;
        this.name = name;
        this.work = work;
        this.interest = interest;


    }

    /**
     * 實例化（Instantiates）一個新的（new）User物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢會員資料時，將每一筆資料新增為一個會員物件
     *
     * @param user_id 會員編號
     * @param email 會員電子信箱
     * @param hashed_password 會員密碼
     * @param name 會員姓名...

     */
    public User(int user_id,String hashed_password, String email, String phone_number, String name, int age, String birthday, boolean gender, String work, String interest) {
        this.user_id=user_id;
    	this.hashed_password = hashed_password;
        this.email = email;
        this.phone_number = phone_number;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.gender = gender;
        this.work = work;
        this.interest = interest;
       

    }

    /**
     * @return the user_id
     */
    public int getID() {
        return this.user_id;
    }
    /**
     * @return the hashed_password
     */
    public String getPassword() {
        return this.hashed_password;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * @return the phone_number
     */
    public String getPhoneNumber() {
        return this.phone_number;
    }
    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return this.birthday;
    }
    /**
     * @return the name
     */
    public boolean getGender() {
        return this.gender;
    }
    /**
     * @return the work
     */
    public String getWork() {
        return this.work;
    }
    /**
     * @return the interest
     */
    public String getInterest() {
        return this.interest;
    }
    /**
     * @return the created_time
     */
    


    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("user_id", getID());
        jso.put("hashed_password", getPassword());
        jso.put("email", getEmail());
        jso.put("phone_number",getPhoneNumber());
        jso.put("name", getName());
        jso.put("age", getAge());
        jso.put("birthday", getBirthday());
        jso.put("gender", getGender());
        jso.put("work", getWork());
        jso.put("interest", getInterest());
        


        return jso;
    }



}
