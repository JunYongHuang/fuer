package kx2_4j.model.repaste;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RepasteVoteUser {
	private long uid;		//投票用户UID
	private String username;		//投票用户姓名
	private int voteitem;		//投票用户投票ID
	
    public RepasteVoteUser(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				username = json.getString("username");
				voteitem = json.getInt("voteitem");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RepasteVoteUser(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public RepasteVoteUser(){
    }
    
	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RepasteVoteUser other = (RepasteVoteUser) obj;
        
		if (username != other.username) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", username=" + username
				+ ", voteitem=" + voteitem
				+ '}';
	}
	
	
	public long getUid()
	{
		return uid;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public int getVoteitem()
	{
		return voteitem;
	}
	

    
    public static RepasteVoteUser[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new RepasteVoteUser[0];
    	}
    	int len = jsons.length();
    	RepasteVoteUser[] objs = new RepasteVoteUser[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new RepasteVoteUser(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}