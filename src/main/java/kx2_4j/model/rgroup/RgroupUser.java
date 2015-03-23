package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.records.RecordUser;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RgroupUser extends RecordUser {
	private int relationship;	//用户与圈子关系 	1：创建者 	2：管理员 	3：成员
    public RgroupUser(Response res) throws KxException {
        super(res);
    }

    protected void init(JSONObject json) throws KxException {
    	super.init(json);
        if (json != null) {
        	try{
				relationship = json.getInt("relationship");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RgroupUser(JSONObject json) throws KxException {
        super(json);
    }
    
	public RgroupUser() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString()
	{
		return '{'
				+ super.toString()
				+ ", relationship=" + relationship
				+ '}';
	}

	public int getRelationship()
	{
		return relationship;
	}
    
    public static RgroupUser[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new RgroupUser[0];
    	}
    	int len = jsons.length();
    	RgroupUser[] objs = new RgroupUser[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new RgroupUser(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}