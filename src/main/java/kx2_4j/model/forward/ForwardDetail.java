package kx2_4j.model.forward;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class ForwardDetail {
	private long uid;		//用户ID
	private String name;		//姓名
	private String logo50;		//50*50头像
	private String ctime;		//创建时间
	private String content;		//内容
	
    public ForwardDetail(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				name = json.getString("name");
				logo50 = json.getString("logo50");
				ctime = json.getString("ctime");
				content = json.getString("content");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public ForwardDetail(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public ForwardDetail(){
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
        ForwardDetail other = (ForwardDetail) obj;
        
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
				+ ", name=" + name
				+ ", logo50=" + logo50
				+ ", ctime=" + ctime
				+ ", content=" + content
				+ '}';
	}
	
	
	public long getUid()
	{
		return uid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getLogo50()
	{
		return logo50;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	
	public String getContent()
	{
		return content;
	}
    
    public static ForwardDetail[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new ForwardDetail[0];
    	}
    	int len = jsons.length();
    	ForwardDetail[] objs = new ForwardDetail[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new ForwardDetail(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}