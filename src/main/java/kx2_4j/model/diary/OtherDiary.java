package kx2_4j.model.diary;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class OtherDiary {
	private long uid;		//用户ID
	private long did;		//日记ID
	private String title;		//日记标题
	private String summary;		//日记摘要
	private String ctime;		//日记创建时间
	
    public OtherDiary(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				did = json.getLong("did");
				title = json.getString("title");
				summary = json.getString("summary");
				ctime = json.getString("ctime");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public OtherDiary(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public OtherDiary(){
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
        OtherDiary other = (OtherDiary) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
		else if (did != other.did) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", did=" + did
				+ ", title=" + title
				+ ", summary=" + summary
				+ ", ctime=" + ctime
				+ '}';
	}
	
	
	public long getUid()
	{
		return uid;
	}
	
	public long getDid()
	{
		return did;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getSummary()
	{
		return summary;
	}
	
	public String getCtime()
	{
		return ctime;
	}
    
    public static OtherDiary[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new OtherDiary[0];
    	}
    	int len = jsons.length();
    	OtherDiary[] objs = new OtherDiary[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new OtherDiary(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}