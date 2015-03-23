
package kx2_4j.model.app;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class Rate_limit_status {
	private int hourly_limit;		//每个小时最大的访问次数
	private int remaining_hits;		//当前剩余的访问次数
	
    public Rate_limit_status(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		hourly_limit = json.getInt("hourly_limit");
				remaining_hits = json.getInt("remaining_hits");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public Rate_limit_status(JSONObject json) throws KxException {
        super();
        init(json);
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
        Rate_limit_status other = (Rate_limit_status) obj;
        
		if (hourly_limit != other.hourly_limit) {
            return false;
        }
        
		else if (remaining_hits != other.remaining_hits) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "hourly_limit=" + hourly_limit
				+ ", remaining_hits=" + remaining_hits
				+ '}';
	}
	
	
	public int getHourly_limit()
	{
		return hourly_limit;
	}
	
	public int getRemaining_hits()
	{
		return remaining_hits;
	}
}