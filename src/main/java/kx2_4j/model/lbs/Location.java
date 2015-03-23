package kx2_4j.model.lbs;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class Location {
	private String content;		//签到内容
	private String pic;		//图片
	private String place_id;		//地点id
	private String loc_name;		//地点名称
	private float lon;		//经度
	private float lat;		//纬度
	private String ctime;		//时间
	
    public Location(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		content = json.getString("content");
				pic = json.getString("pic");
				place_id = json.getString("place_id");
				loc_name = json.getString("loc_name");
				lon = (float) json.getDouble("lon");
				lat = (float) json.getDouble("lat");
				ctime = json.getString("ctime");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public Location(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public Location(){
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
        Location other = (Location) obj;
        
		if (place_id != other.place_id) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "content=" + content
				+ ", pic=" + pic
				+ ", place_id=" + place_id
				+ ", loc_name=" + loc_name
				+ ", lon=" + lon
				+ ", lat=" + lat
				+ ", ctime=" + ctime
				+ '}';
	}
	
	
	public String getContent()
	{
		return content;
	}
	
	public String getPic()
	{
		return pic;
	}
	
	public String getPlace_id()
	{
		return place_id;
	}
	
	public String getLoc_name()
	{
		return loc_name;
	}
	
	public float getLon()
	{
		return lon;
	}
	
	public float getLat()
	{
		return lat;
	}
	
	public String getCtime()
	{
		return ctime;
	}
}