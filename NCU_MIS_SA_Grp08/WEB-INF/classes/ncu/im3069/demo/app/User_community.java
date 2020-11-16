package ncu.im3069.demo.app;
import org.json.*;
public class User_community {

	private int user_community_id;
	private int user_id;
	private int community_id;
	private boolean isHost;
	private boolean isHostApproved;
	private boolean isMemberApproved;
	
	public User_community(int user_id,int community_id) {
		this.user_id=user_id;
		this.community_id=community_id;
	}
	
	public User_community(int user_community_id,int user_id,int community_id,boolean isHost,boolean isHostApproved,boolean isMemberApproved) {
		this.user_id=user_id;
		this.user_community_id=user_community_id;
		this.community_id=community_id;
		this.isHost=isHost;
		this.isHostApproved=isHostApproved;
		this.isMemberApproved=isMemberApproved;
	}
	
	public int getID() {
		return this.user_community_id;
	}
	public int getUser() {
		return this.user_id;
	}
	public int getCommunity() {
		return this.community_id;
	}
	public boolean getHost() {
		return this.isHost;
	}
	public boolean getHostApproved() {
		return this.isHostApproved;
	}
	public boolean getMemberApproved() {
		return this.isMemberApproved;
	}
	
	public JSONObject getData() {
		JSONObject data=new JSONObject();
		data.put("user_community_id", user_community_id);
		data.put("user_id", user_id);
		data.put("community_id", community_id);
		data.put("isHost",isHost);
		data.put("isHostApproved", isHostApproved);
		data.put("isMemberApproved",isMemberApproved);
		return data;
	}
}
