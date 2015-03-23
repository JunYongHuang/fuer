
package kx2_4j.model.photo;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class PhotoLatest {
	private long uid;		//好友的uid
	private int num;		//传照片数量
	private String time;		//上传时间
	private PhotoInfo[] photoinfo;		//照片信息
	
    public PhotoLatest(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				num = json.getInt("num");
				time = json.getString("time");
				photoinfo = PhotoInfo.jsons2objs(json.getJSONArray("photoinfo"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public PhotoLatest(JSONObject json) throws KxException {
        super();
        init(json);
    }
    
	public PhotoLatest() {
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
        PhotoLatest other = (PhotoLatest) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
		else if (num != other.num) {
            return false;
        }
        
		else if (time != other.time) {
            return false;
        }
        
		else if (photoinfo != other.photoinfo) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", num=" + num
				+ ", time=" + time
				+ ", photoinfo=" + photoinfo
				+ '}';
	}
	
	
	public long getUid()
	{
		return uid;
	}
	
	public int getNum()
	{
		return num;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public PhotoInfo[] getPhotoinfo()
	{
		return photoinfo;
	}
	
    public static PhotoLatest[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new PhotoLatest[0];
    	}
    	int len = jsons.length();
    	PhotoLatest[] objs = new PhotoLatest[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new PhotoLatest(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}