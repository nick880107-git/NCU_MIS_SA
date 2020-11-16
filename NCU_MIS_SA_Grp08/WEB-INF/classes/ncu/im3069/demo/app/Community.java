package ncu.im3069.demo.app;


import org.json.*;
public class Community {
	
	/** name:社群名稱*/
	private String name;
	
	/** content:社群介紹*/
	private String content;
	
	/** total_member:成員數量*/
	private int total_member;
	
	/** id:社群id*/
	private int community_id;
	

	/** ch:CommunityHelper之物件與Community相關之資料庫方法 */
	private CommunityHelper ch=CommunityHelper.getHelper();
	
	
	/** 實例化（Instantiates）一個新的（new）Community 物件 */
	/** 採用多載（overload）方法進行，此建構子用於建立社群    */
    public Community(String name,String content) {
        this.name = name;
        this.content = content;
        this.total_member=1;
        
        
    }
    
    /** 採用多載（overload）方法進行，此建構子用於更新社群資料*/
    public Community(int id,String name,String content) {
    	this.community_id=id;
    	this.name=name;
    	this.content=content;
    	
    }
    
    public Community(int id,int total_member) {
    	this.community_id=id;
    	this.total_member=total_member;
    	ch.updateTotalMember(this);
    }
    
    /** 採用多載（overload）方法進行，此建構子用於查詢社群資料*/
    public Community(int id,String name,String content,int total_member) {
    	this.community_id=id;
    	this.name=name;
    	this.content=content;
    	this.total_member=total_member;
    	
    }
    
    public String getName() {
    	return this.name;
    }
    
    public String getContent() {
    	return this.content;
    }
    
    public int getTotal_member() {
    	return this.total_member;
    }
    
    public int getID() {
    	return this.community_id;
    }
    
    public JSONObject getData() {
    	JSONObject jso = new JSONObject();
        jso.put("community_id", getID());
        jso.put("name", getName());
        jso.put("content", getContent());
        jso.put("total_member", getTotal_member());
      
        
        return jso;
    }
  
}
