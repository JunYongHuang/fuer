package kx2_4j.model.repaste;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class OtherRepaste extends RepasteModel {
	private int r_online;		//转贴人的在线状态
	private String r_logo50;		//转贴人的头像(50 X 50)
	
    public OtherRepaste(Response res) throws KxException {
        super(res);
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		r_online = json.getInt("r_online");
				r_logo50 = json.getString("r_logo50");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public OtherRepaste(JSONObject json) throws KxException {
        super(json);
        init(json);
    }

    public OtherRepaste(){
    }
    
	@Override
	public String toString()
	{
		return '{'
				+super.toString()
				+ ", r_online=" + r_online
				+ ", r_logo50=" + r_logo50
				+ '}';
	}
	
	
	public int getR_online()
	{
		return r_online;
	}
	
	public String getR_logo50()
	{
		return r_logo50;
	}
    
    public static OtherRepaste[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new OtherRepaste[0];
    	}
    	int len = jsons.length();
    	OtherRepaste[] objs = new OtherRepaste[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new OtherRepaste(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}