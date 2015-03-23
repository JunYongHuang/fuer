package kx2_4j.model.app;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class AppStatus{
	private long uid;		//用户ID
	private int install;//组件安装状态 		0: 未安装 		1: 已安装
	
    public AppStatus(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
        		install = json.getInt("install");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public AppStatus(JSONObject json) throws KxException {
        super();
        init(json);
    }
    
	public AppStatus() {
		// TODO Auto-generated constructor stub
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
        AppStatus other = (AppStatus) obj;
        if (uid != other.uid) {
            return false;
        }
        else if (install != other.install) {
            return false;
        }
        return true;
    }
	
	@Override
	public String toString()
	{
		return "{"
				+ "uid=" + uid
				+ ", install=" + install
				+ "}";
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public int getInstall()
	{
		return install;
	}
	
    public static AppStatus[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new AppStatus[0];
    	}
    	int len = jsons.length();
    	AppStatus[] objs = new AppStatus[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new AppStatus(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}