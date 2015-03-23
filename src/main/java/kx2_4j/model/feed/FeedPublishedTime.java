package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedPublishedTime {
	private String ctime;		//标准时间，如：2012-02-20 10:48:51
	private String stime;		//格式化的时间，如：02月20日 10:48
	
    public FeedPublishedTime(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		ctime = json.getString("ctime");
				stime = json.getString("stime");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedPublishedTime(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedPublishedTime(){
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
        FeedPublishedTime other = (FeedPublishedTime) obj;
        
		if (ctime != other.ctime) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "ctime=" + ctime
				+ ", stime=" + stime
				+ '}';
	}
	
	
	public String getCtime()
	{
		return ctime;
	}
	
	public String getStime()
	{
		return stime;
	}
}