package ncu.im3069.demo.app;
import org.json.*;

public class FriendMatch {
    private int user1_id;
    private int user2_id;
    private int match_id;
    
    private boolean match_state;
    
    
    public FriendMatch(int user1_id,int user2_id) {
    		this.user1_id=user1_id;
    		this.user2_id=user2_id;
    	   	this.match_state=false;
    }
    public FriendMatch(int match_id,int user1_id,int user2_id,boolean match_state) {
    	this.user1_id=user1_id;
    	this.user2_id=user2_id;
    	this.match_id=match_id;
    	this.match_state=match_state;
    	
    }
    public int getID() {
    	return match_id;
    }
    public int getUser1(){
    	return user1_id;
    }
    public int getUser2(){
    	return user2_id;
    }
   
    public boolean getMatchstate() {
    	return match_state;
    }
   
    public JSONObject getData() {
    	JSONObject data=new JSONObject();
    	data.put("match_id",getID());
    	data.put("user1_id",getUser1());
    	data.put("user2_id",getUser2());
    	
    	data.put("match_state",getMatchstate());
    	
    	
    	return data;
    	
    }
}