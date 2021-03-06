package kx2_4j.model.lbs;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class LBSFriend {
	private long uid;		//用户UID
	private String name;		//用户名
	private String logo50;		//50*50头像
	private String place_id;		//地点id
	private String loc_name;		//地点名称
	private float lon;		//经度
	private float lat;		//纬度
	private float distance;		//与我的距离
	private String ctime;		//时间
	
    public LBSFriend(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				name = json.getString("name");
				logo50 = json.getString("logo50");
				place_id = json.getString("place_id");
				loc_name = json.getString("loc_name");
				lon = (float) json.getDouble("lon");
				lat = (float) json.getDouble("lat");
				distance = (float) json.getDouble("distance");
				ctime = json.getString("ctime");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public LBSFriend(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public LBSFriend(){
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
        LBSFriend other = (LBSFriend) obj;
        
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
				+ ", place_id=" + place_id
				+ ", loc_name=" + loc_name
				+ ", lon=" + lon
				+ ", lat=" + lat
				+ ", distance=" + distance
				+ ", ctime=" + ctime
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
	
	public float getDistance()
	{
		return distance;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	

    
    public static LBSFriend[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new LBSFriend[0];
    	}
    	int len = jsons.length();
    	LBSFriend[] objs = new LBSFriend[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new LBSFriend(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}