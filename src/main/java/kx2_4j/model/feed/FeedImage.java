package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedImage {
	private long pid;		//照片id
	private long uid;		//上传者id
	private long albumid;		//照片专辑id
	private String pic;		//原始图地址
	private int pos;		//照片在专辑中的位置
	private int privacy;		//照片查看权限
	private String pic_cover;		//缩略图地址
	
    public FeedImage(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		pid = json.getLong("pid");
				uid = json.getLong("uid");
				albumid = json.getLong("albumid");
				pic = json.getString("pic");
				pos = json.getInt("pos");
				privacy = json.getInt("privacy");
				pic_cover = json.getString("pic_cover");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedImage(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedImage(){
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
        FeedImage other = (FeedImage) obj;
        
		if (pid != other.pid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "pid=" + pid
				+ ", uid=" + uid
				+ ", albumid=" + albumid
				+ ", pic=" + pic
				+ ", pos=" + pos
				+ ", privacy=" + privacy
				+ ", pic_cover=" + pic_cover
				+ '}';
	}
	
	
	public long getPid()
	{
		return pid;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public long getAlbumid()
	{
		return albumid;
	}
	
	public String getPic()
	{
		return pic;
	}
	
	public int getPos()
	{
		return pos;
	}
	
	public int getPrivacy()
	{
		return privacy;
	}
	
	public String getPic_cover()
	{
		return pic_cover;
	}
    
    public static FeedImage[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new FeedImage[0];
    	}
    	int len = jsons.length();
    	FeedImage[] objs = new FeedImage[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new FeedImage(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}