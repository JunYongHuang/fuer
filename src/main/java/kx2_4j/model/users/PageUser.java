package kx2_4j.model.users;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class PageUser extends UserBase {
	
    public PageUser()
    {
    	
    }
    public PageUser(Response res) throws KxException {
        super(res);
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
            try {
                uid = json.getLong("pageid");
            } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public PageUser(JSONObject json) throws KxException {
        super(json);
        init(json);
    }
	
    public String toString()
    {
    	return "page{"
    			+ "pageid=" + uid
    			+ ", name=" + name
    			+ ",logo50=" + logo50
    			+'}';
    }

    
    public static PageUser[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new PageUser[0];
    	}
    	int len = jsons.length();
    	PageUser[] objs = new PageUser[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new PageUser(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}