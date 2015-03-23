package kx2_4j.model.like;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.users.UserBase;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class LikeUser extends UserBase {
	private String city;		//用户现居住地
	private String status_latest;		//用户个人签名
	
    public LikeUser(Response res) throws KxException {
        super(res);
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		city = json.getString("city");
				status_latest = json.getString("status_latest");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public LikeUser(JSONObject json) throws KxException {
        super(json);
        init(json);
    }

    public LikeUser(){
    }
	
	@Override
	public String toString()
	{
		return "User{"
                + "uid=" + getUid()
                + ", name='" + getName() + '\''
                + ", logo50='" + getLogo50() + '\''
				+ ", city=" + city
				+ ", status_latest=" + status_latest
				+ '}';
	}
	
	
	public String getCity()
	{
		return city;
	}
	
	public String getStatus_latest()
	{
		return status_latest;
	}
    
    public static LikeUser[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new LikeUser[0];
    	}
    	int len = jsons.length();
    	LikeUser[] objs = new LikeUser[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new LikeUser(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}