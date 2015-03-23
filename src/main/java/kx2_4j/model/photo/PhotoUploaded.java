
package kx2_4j.model.photo;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class PhotoUploaded {
	private long uid;		//用户ID
	private long albumid;		//照片专辑ID
	private long pid;		//照片ID
	private String src;		//照片(默认大小)
	
    public PhotoUploaded(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				albumid = json.getLong("albumid");
				pid = json.getLong("pid");
				src = json.getString("src");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public PhotoUploaded(JSONObject json) throws KxException {
        super();
        init(json);
    }

	public PhotoUploaded() {
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
        PhotoUploaded other = (PhotoUploaded) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
		else if (albumid != other.albumid) {
            return false;
        }
        
		else if (pid != other.pid) {
            return false;
        }
        
		else if (src != other.src) {
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
				+ ", src=" + src
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
	
	public String getPic()
	{
		return src;
	}
}