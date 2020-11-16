package ncu.im3069.demo.app;
import org.json.*;



public class Message {

	private int message_id;
	private int match_id;
	private int user_id;
	
	private String message_content;
	
	/** 新增建構子 **/
	public Message(int match_id,int user_id,String message_content) {
		this.match_id=match_id;
		this.user_id=user_id;
		this.message_content=message_content;
	}
	
	/** 查詢建構子 **/
	public Message(int message_id,int match_id,int user_id,String message_content) {
		this.message_id=message_id;
		this.match_id=match_id;
		this.user_id=user_id;
		
		this.message_content=message_content;
	}
	
	public int getMessageID() {
		return message_id;
	}
	public int getMatchID() {
		return match_id;
	}
	public int getUserID() {
		return user_id;
	}
	
	public String getMessageContent() {
		return message_content;
	}
	public JSONObject getData() {
		JSONObject data=new JSONObject();
		data.put("message_id", message_id);
		data.put("match_id", match_id);
		data.put("user_id", user_id);
	
		data.put("message_content", message_content);
		
		return data;
	}
}
