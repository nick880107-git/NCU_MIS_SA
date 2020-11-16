package ncu.im3069.demo.app;


import org.json.*;
public class Post {
	private int post_id;
	private int user_id;
	private int community_id;
	
	private String content;
	
	
	/** 新增建構子 */
	public Post(int user_id,int community_id,String content) {
		this.user_id=user_id;
		this.community_id=community_id;
		this.content=content;
		
		
	}
	
	/** 查詢建構子 */
	public Post(int post_id,int user_id,int community_id,String content) {
		this.post_id=post_id;
		this.user_id=user_id;
		this.community_id=community_id;
		this.content=content;
		
		
	}
	
	/** 更新建構子 */
	public Post(int post_id,String content) {
		this.post_id=post_id;
		this.content=content;
		
	}
	
	public int getPostID() {
		return this.post_id;
	}
	public int getUserID() {
		return this.user_id;
	}
	public int getCommunityID() {
		return this.community_id;
	}
	
	
	public String getContent() {
		return this.content;
	}
	
	
	public JSONObject getData() {
		JSONObject data=new JSONObject();
		data.put("post_id", getPostID());
		data.put("user_id",getUserID());
		data.put("community_id",getCommunityID());
		
		data.put("content", getContent());
		
		
		return data;
	}



}
