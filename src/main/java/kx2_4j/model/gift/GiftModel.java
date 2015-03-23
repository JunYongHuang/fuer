package kx2_4j.model.gift;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GiftModel {
	private long gid;		//礼物id
	private String pic;		//礼物图片链接
	private String gname;		//礼物名称
	
    public GiftModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		gid = json.getLong("gid");
				pic = json.getString("pic");
				gname = json.getString("gname");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GiftModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public GiftModel(){
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
        GiftModel other = (GiftModel) obj;
        
		if (gid != other.gid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "gid=" + gid
				+ ", pic=" + pic
				+ ", gname=" + gname
				+ '}';
	}
	
	
	public long getGid()
	{
		return gid;
	}
	
	public String getPic()
	{
		return pic;
	}
	
	public String getGname()
	{
		return gname;
	}
    
    public static GiftModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GiftModel[0];
    	}
    	int len = jsons.length();
    	GiftModel[] objs = new GiftModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GiftModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}