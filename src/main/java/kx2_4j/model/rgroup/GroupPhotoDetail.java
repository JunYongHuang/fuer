package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GroupPhotoDetail {
	private long pid;		//图片ID
	private long tid;		//圈子动态ID
	private String albumtitle;		//专辑名称
	private long albumid;		//专辑ID
	private String title;		//专辑ID
	private String s_pic;		//小图片
	private String f_pic;		//大图片
	private long ctime;		//创建时间
	
    public GroupPhotoDetail(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		pid = json.getLong("pid");
				tid = json.getLong("tid");
				albumtitle = json.getString("albumtitle");
				albumid = json.getLong("albumid");
				title = json.getString("title");
				s_pic = json.getString("s_pic");
				f_pic = json.getString("f_pic");
				ctime = json.getLong("ctime");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GroupPhotoDetail(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public GroupPhotoDetail(){
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
        GroupPhotoDetail other = (GroupPhotoDetail) obj;
        
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
				+ ", tid=" + tid
				+ ", albumtitle=" + albumtitle
				+ ", albumid=" + albumid
				+ ", title=" + title
				+ ", s_pic=" + s_pic
				+ ", f_pic=" + f_pic
				+ ", ctime=" + ctime
				+ '}';
	}
	
	
	public long getPid()
	{
		return pid;
	}
	
	public long getTid()
	{
		return tid;
	}
	
	public String getAlbumtitle()
	{
		return albumtitle;
	}
	
	public long getAlbumid()
	{
		return albumid;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getS_pic()
	{
		return s_pic;
	}
	
	public String getF_pic()
	{
		return f_pic;
	}
	
	public long getCtime()
	{
		return ctime;
	}
}