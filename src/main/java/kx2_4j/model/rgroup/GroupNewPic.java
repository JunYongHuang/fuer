
package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GroupNewPic {
	private String s_pic;		//小图片url
	private String f_pic;		//大图片url
	private long pid;		//图片id
	private long aid;		//图片所在专辑id
	
    public GroupNewPic(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		s_pic = json.getString("s_pic");
				f_pic = json.getString("f_pic");
				pid = json.getLong("pid");
				aid = json.getLong("aid");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GroupNewPic(JSONObject json) throws KxException {
        super();
        init(json);
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
        GroupNewPic other = (GroupNewPic) obj;
        
		if (pid != other.pid) {
            return false;
        }
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "s_pic=" + s_pic
				+ ", f_pic=" + f_pic
				+ ", pid=" + pid
				+ ", aid=" + aid
				+ '}';
	}
	
	
	public String getS_pic()
	{
		return s_pic;
	}
	
	public String getF_pic()
	{
		return f_pic;
	}
	
	public long getPid()
	{
		return pid;
	}
	
	public long getAid()
	{
		return aid;
	}
}