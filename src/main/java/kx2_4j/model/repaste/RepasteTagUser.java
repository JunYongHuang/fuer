package kx2_4j.model.repaste;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RepasteTagUser {
	private long rpid;		//该用户转帖id
	private long uid;		//选择该TAG的UID
	private String username;		//选择该TAG的用户名
	private long tagid;		//选择该TAG的id
	private String ctime;		//选择该TAG的时间
	
    public RepasteTagUser(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		rpid = json.getLong("rpid");
				uid = json.getLong("uid");
				username = json.getString("username");
				tagid = json.getLong("tagid");
				ctime = json.getString("ctime");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RepasteTagUser(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public RepasteTagUser(){
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
        RepasteTagUser other = (RepasteTagUser) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "rpid=" + rpid
				+ ", uid=" + uid
				+ ", username=" + username
				+ ", tagid=" + tagid
				+ ", ctime=" + ctime
				+ '}';
	}
	
	
	public long getRpid()
	{
		return rpid;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public long getTagid()
	{
		return tagid;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	

    
    public static RepasteTagUser[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new RepasteTagUser[0];
    	}
    	int len = jsons.length();
    	RepasteTagUser[] objs = new RepasteTagUser[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new RepasteTagUser(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}