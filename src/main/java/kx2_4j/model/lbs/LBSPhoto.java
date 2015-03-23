package kx2_4j.model.lbs;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class LBSPhoto {
	private String place_id;		//地点id
	private String loc_name;		//地点名称
	private float lon;		//经度
	private float lat;		//纬度
	private float distance;		//与我的距离
	private LBSPic[] pics;		//图片信息
	
    public LBSPhoto(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		place_id = json.getString("place_id");
				loc_name = json.getString("loc_name");
				lon = (float) json.getDouble("lon");
				lat = (float) json.getDouble("lat");
				distance = (float) json.getDouble("distance");
				pics = LBSPic.jsons2objs(json.getJSONArray("pics"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public LBSPhoto(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public LBSPhoto(){
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
        LBSPhoto other = (LBSPhoto) obj;
        
		if (place_id != other.place_id) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "place_id=" + place_id
				+ ", loc_name=" + loc_name
				+ ", lon=" + lon
				+ ", lat=" + lat
				+ ", distance=" + distance
				+ ", pics=" + pics
				+ '}';
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
	
	public LBSPic[] getPics()
	{
		return pics;
	}
    
    public static LBSPhoto[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new LBSPhoto[0];
    	}
    	int len = jsons.length();
    	LBSPhoto[] objs = new LBSPhoto[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new LBSPhoto(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}