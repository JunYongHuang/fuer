
package kx2_4j.model.photo;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class PhotoInfo {
	private long pid;		//上传图片的pid
	private long albumid;		//照片所在专辑的albumid
	
    public PhotoInfo(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		pid = json.getLong("pid");
				albumid = json.getLong("albumid");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public PhotoInfo(JSONObject json) throws KxException {
        super();
        init(json);
    }

	public PhotoInfo() {
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
        PhotoInfo other = (PhotoInfo) obj;
        
		if (pid != other.pid) {
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
				+ "pid=" + pid
				+ ", albumid=" + albumid
				+ '}';
	}
	
	
	public long getPid()
	{
		return pid;
	}
	
	public long getAlbumid()
	{
		return albumid;
	}
	
    public static PhotoInfo[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new PhotoInfo[0];
    	}
    	int len = jsons.length();
    	PhotoInfo[] objs = new PhotoInfo[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new PhotoInfo(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}