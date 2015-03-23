package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.records.RecordUser;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GroupTalkN extends GroupTalk {
	private RecordUser userinfo;		//发布动态的用户
	
    public GroupTalkN(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		userinfo = new RecordUser(json.getJSONObject("userinfo"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GroupTalkN(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public GroupTalkN(){
    }
    
	@Override
	public String toString()
	{
		return '{'
				+super.toString()
				+ "userinfo=" + userinfo
				+ '}';
	}
	
	
	public RecordUser getUserinfo()
	{
		return userinfo;
	}
    
    public static GroupTalkN[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GroupTalkN[0];
    	}
    	int len = jsons.length();
    	GroupTalkN[] objs = new GroupTalkN[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GroupTalkN(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}