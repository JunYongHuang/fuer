
package kx2_4j.model.photo;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class PhotoModel {
	private long uid;		//用户ID
	private long albumid;		//照片专辑ID
	private long pid;		//照片ID
	private String title;		//照片标题
	private String pic;		//照片(默认大小)
	private String pic_small;		//照片(小图)
	private String pic_mid;		//照片(中图)
	private String pic_big;		//照片(大图)
	private String ctime;		//照片上传时间
	
    public PhotoModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				albumid = json.getLong("albumid");
				pid = json.getLong("pid");
				title = json.getString("title");
				pic = json.getString("pic");
				pic_small = json.getString("pic_small");
				pic_mid = json.getString("pic_mid");
				pic_big = json.getString("pic_big");
				ctime = json.getString("ctime");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public PhotoModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

	public PhotoModel() {
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
        PhotoModel other = (PhotoModel) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
		else if (albumid != other.albumid) {
            return false;
        }
        
		else if (pid != other.pid) {
            return false;
        }
        
		else if (title != other.title) {
            return false;
        }
        
		else if (pic != other.pic) {
            return false;
        }
        
		else if (pic_small != other.pic_small) {
            return false;
        }
        
		else if (pic_mid != other.pic_mid) {
            return false;
        }
        
		else if (pic_big != other.pic_big) {
            return false;
        }
        
		else if (ctime != other.ctime) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", albumid=" + albumid
				+ ", pid=" + pid
				+ ", title=" + title
				+ ", pic=" + pic
				+ ", pic_small=" + pic_small
				+ ", pic_mid=" + pic_mid
				+ ", pic_big=" + pic_big
				+ ", ctime=" + ctime
				+ '}';
	}
	
	
	public long getUid()
	{
		return uid;
	}
	
	public long getAlbumid()
	{
		return albumid;
	}
	
	public long getPid()
	{
		return pid;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getPic()
	{
		return pic;
	}
	
	public String getPic_small()
	{
		return pic_small;
	}
	
	public String getPic_mid()
	{
		return pic_mid;
	}
	
	public String getPic_big()
	{
		return pic_big;
	}
	
	public String getCtime()
	{
		return ctime;
	}
    
    public static PhotoModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new PhotoModel[0];
    	}
    	int len = jsons.length();
    	PhotoModel[] objs = new PhotoModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new PhotoModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}