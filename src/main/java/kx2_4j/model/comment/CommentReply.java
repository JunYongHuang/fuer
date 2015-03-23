package kx2_4j.model.comment;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class CommentReply {
	private long cid;		//评论id
	private String ctime;		//发评论时间
	private String content;		//所发评论的内容
	private String name;		//发评论用户姓名
	private long uid;		//发评论用户的UID
	private String logo50;		//50×50头像
	private int online;		//该用户是否在线（0，不在线；1，在线）
	private int hidden;		//是否为悄悄话（0，不是悄悄话；1，悄悄话）
	
    public CommentReply(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		cid = json.getLong("cid");
				ctime = json.getString("ctime");
				content = json.getString("content");
				name = json.getString("name");
				uid = json.getLong("uid");
				logo50 = json.getString("logo50");
				online = json.getInt("online");
				hidden = json.getInt("hidden");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public CommentReply(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public CommentReply(){
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
        CommentReply other = (CommentReply) obj;
        
		if (cid != other.cid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "cid=" + cid
				+ ", ctime=" + ctime
				+ ", content=" + content
				+ ", name=" + name
				+ ", uid=" + uid
				+ ", logo50=" + logo50
				+ ", online=" + online
				+ ", hidden=" + hidden
				+ '}';
	}
	
	
	public long getCid()
	{
		return cid;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getName()
	{
		return name;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public String getLogo50()
	{
		return logo50;
	}
	
	public int getOnline()
	{
		return online;
	}
	
	public int getHidden()
	{
		return hidden;
	}
    
    public static CommentReply[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new CommentReply[0];
    	}
    	int len = jsons.length();
    	CommentReply[] objs = new CommentReply[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new CommentReply(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}