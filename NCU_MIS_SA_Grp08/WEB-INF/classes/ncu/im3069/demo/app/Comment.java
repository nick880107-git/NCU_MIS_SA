package ncu.im3069.demo.app;
import org.json.*;
public class Comment {
	private int post_id;
	private int comment_id;
	
	private String content;
	
	/** 新增建構子 **/
	public Comment(int post_id,String content) {
		this.post_id=post_id;
		this.content=content;
	}
	
	public Comment(int post_id,int comment_id,String content) {
		this.post_id=post_id;
		this.comment_id=comment_id;
		
		this.content=content;
	}
	
	public int getPostID() {
		return post_id;
	}
	public int getCommentID() {
		return comment_id;
	}
	
	public String getContent() {
		return content;
	}
	public JSONObject getData() {
		JSONObject data=new JSONObject();
		data.put("post_id", getPostID());
		data.put("comment_id", getCommentID());
		
		data.put("content", getContent());
		return data;
	}
}
