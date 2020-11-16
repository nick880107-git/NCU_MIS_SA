package ncu.im3069.demo.app;
import org.json.*;
public class Inbox {

	private int inbox_id;
	private int user_id;
	private int friend_match_id=-1;
	private int user_community_id=-1;
	
	public Inbox(int user_id) {
		this.user_id=user_id;
	}
	
	public Inbox(int inbox_id,int user_id,int friend, int community) {
		this.inbox_id=inbox_id;
		this.user_id=user_id;
		this.friend_match_id=friend;
		this.user_community_id=community;
	}
	public void setFriend(int id) {
		this.friend_match_id=id;
	}
	public void setCommunity(int id) {
		this.user_community_id=id;
	}
	public int getID() {
		return inbox_id;
	}
	public int getUser() {
		return user_id;
	}
	public int getFriend() {
		return friend_match_id;
	}
	public int getCommunity() {
		return user_community_id;
	}
	public JSONObject getData() {
		JSONObject data=new JSONObject();
		data.put("inbox_id",inbox_id);
		data.put("user_id",user_id);
		data.put("friend_match_id",friend_match_id);
		data.put("user_community_id",user_community_id);
		return data;
	}
}
