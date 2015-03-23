package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedLbs {
	private String loc_name;		//地点名称
	private double lon;		//经度
	private double lat;		//纬度
	
    public FeedLbs(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		loc_name = json.getString("loc_name");
				lon = json.getDouble("lon");
				lat = json.getDouble("lat");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedLbs(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedLbs(){
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
        FeedLbs other = (FeedLbs) obj;
        
		if (loc_name != other.loc_name) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "loc_name=" + loc_name
				+ ", lon=" + lon
				+ ", lat=" + lat
				+ '}';
	}
	
	
	public String getLoc_name()
	{
		return loc_name;
	}
	
	public double getLon()
	{
		return lon;
	}
	
	public double getLat()
	{
		return lat;
	}
}