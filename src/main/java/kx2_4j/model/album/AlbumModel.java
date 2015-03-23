
package kx2_4j.model.album;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class AlbumModel{
	private long uid;		//用户ID
	private long albumid;		//照片专辑ID
	private String title;		//照片专辑名称
	private String ctime;		//照片专辑创建时间
	private String coverpic;		//照片专辑封面
	private int picnum;		//专辑内照片数量
	private String mtime;		//专辑最近修改时间
	private int privacy;		//照片专辑隐私设置(0:任何人可见,1:仅好友可见,2:凭密码,3:隐藏)
	
    public AlbumModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				albumid = json.getLong("albumid");
				title = json.getString("title");
				ctime = json.getString("ctime");
				coverpic = json.getString("coverpic");
				picnum = json.getInt("picnum");
				mtime = json.getString("mtime");
				privacy = json.getInt("privacy");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public AlbumModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

	
	public AlbumModel() {
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
        AlbumModel other = (AlbumModel) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
		else if (albumid != other.albumid) {
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
				+ ", title=" + title
				+ ", ctime=" + ctime
				+ ", coverpic=" + coverpic
				+ ", picnum=" + picnum
				+ ", mtime=" + mtime
				+ ", privacy=" + privacy
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
	
	public String getTitle()
	{
		return title;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	
	public String getCoverpic()
	{
		return coverpic;
	}
	
	public int getPicnum()
	{
		return picnum;
	}
	
	public String getMtime()
	{
		return mtime;
	}
	
	public int getPrivacy()
	{
		return privacy;
	}
    
    public static AlbumModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new AlbumModel[0];
    	}
    	int len = jsons.length();
    	AlbumModel[] objs = new AlbumModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new AlbumModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}