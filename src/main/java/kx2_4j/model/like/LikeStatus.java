package kx2_4j.model.like;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class LikeStatus {
	private long uid;		//用户uid
	private int liked;		//用户是否赞过 0:未赞过 1:赞过
	
    public LikeStatus(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				liked = json.getInt("liked");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public LikeStatus(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public LikeStatus(){
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
        LikeStatus other = (LikeStatus) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", liked=" + liked
				+ '}';
	}
	
	
	public long getUid()
	{
		return uid;
	}
	
	public int getLiked()
	{
		return liked;
	}
    
    public static LikeStatus[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new LikeStatus[0];
    	}
    	int len = jsons.length();
    	LikeStatus[] objs = new LikeStatus[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new LikeStatus(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}