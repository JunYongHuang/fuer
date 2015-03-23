package kx2_4j.model.comment;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class CommentSource {
	private String objtype;		//评论资源类型名称
	private String pic;		//评论资源为照片或照片专辑时，返回pic；
	private String place;		//评论资源为位置服务时，返回place；
	private String title;		//其余类型返回title
	private String url;		//对应链接
	private long c_total;		//资源评论总数
	private long l_total;		//资源赞总数
	private long uid;		//资源所有者uid
	private String name;		//资源所有者姓名
	
    public CommentSource(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		objtype = json.getString("objtype");
				pic = json.getString("pic");
				place = json.getString("place");
				title = json.getString("title");
				url = json.getString("url");
				c_total = json.getLong("c_total");
				l_total = json.getLong("l_total");
				uid = json.getLong("uid");
				name = json.getString("name");

	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public CommentSource(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public CommentSource(){
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
        CommentSource other = (CommentSource) obj;
        
		if (url != other.url) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "objtype=" + objtype
				+ ", pic=" + pic
				+ ", place=" + place
				+ ", title=" + title
				+ ", url=" + url
				+ ", c_total=" + c_total
				+ ", l_total=" + l_total
				+ ", uid=" + uid
				+ ", name=" + name
				+ '}';
	}
	
	
	public String getObjtype()
	{
		return objtype;
	}
	
	public String getPic()
	{
		return pic;
	}
	
	public String getPlace()
	{
		return place;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public long getC_total()
	{
		return c_total;
	}
	
	public long getL_total()
	{
		return l_total;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public String getName()
	{
		return name;
	}
}