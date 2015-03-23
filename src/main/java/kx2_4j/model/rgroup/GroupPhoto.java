package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.records.RecordUser;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GroupPhoto {
	private String title;		//文字内容
	private long pid;		//照片id
	private long tid;		//动态id
	private long albumid;		//照片专辑id
	private long ctime;		//创建时间
	private String s_pic;		//照片小图地址
	private String f_pic;		//照片大图地址
	private RecordUser user;		//用户信息
	
    public GroupPhoto(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		title = json.getString("title");
				pid = json.getLong("pid");
				tid = json.getLong("tid");
				albumid = json.getLong("albumid");
				ctime = json.getLong("ctime");
				s_pic = json.getString("s_pic");
				f_pic = json.getString("f_pic");
				user = new RecordUser(json.getJSONObject("user"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GroupPhoto(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public GroupPhoto(){
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
        GroupPhoto other = (GroupPhoto) obj;
        
		if (pid != other.pid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "title=" + title
				+ ", pid=" + pid
				+ ", tid=" + tid
				+ ", albumid=" + albumid
				+ ", ctime=" + ctime
				+ ", s_pic=" + s_pic
				+ ", f_pic=" + f_pic
				+ ", user=" + user
				+ '}';
	}
	
	
	public String getTitle()
	{
		return title;
	}
	
	public long getPid()
	{
		return pid;
	}
	
	public long getTid()
	{
		return tid;
	}
	
	public long getAlbumid()
	{
		return albumid;
	}
	
	public long getCtime()
	{
		return ctime;
	}
	
	public String getS_pic()
	{
		return s_pic;
	}
	
	public String getF_pic()
	{
		return f_pic;
	}
	
	public RecordUser getUser()
	{
		return user;
	}
    
    public static GroupPhoto[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GroupPhoto[0];
    	}
    	int len = jsons.length();
    	GroupPhoto[] objs = new GroupPhoto[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GroupPhoto(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}