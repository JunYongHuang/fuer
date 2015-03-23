package kx2_4j.model.comment;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class CommentModel {
	private long thread_cid;		//评论回复id
	private String ctime;		//发评论时间
	private String content;		//所发评论的内容
	private String name;		//发评论用户姓名
	private long uid;		//发评论用户的UID
	private String logo50;		//50×50头像
	private int online;		//该用户是否在线（0，不在线；1，在线）
	private int hidden;		//是否为悄悄话（0，不是悄悄话；1，悄悄话）
	private CommentReply[] replys;		//评论回复
	
    public CommentModel(Response res) throws KxException, JSONException {
        init(res.asJSONObject().getJSONObject("data"));
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		thread_cid = json.getLong("thread_cid");
				ctime = json.getString("ctime");
				content = json.getString("content");
				name = json.getString("name");
				uid = json.getLong("uid");
				logo50 = json.getString("logo50");
				online = json.getInt("online");
				hidden = json.getInt("hidden");
				replys = CommentReply.jsons2objs(json.getJSONArray("replys"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public CommentModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public CommentModel(){
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
        CommentModel other = (CommentModel) obj;
        
		if (thread_cid != other.thread_cid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "thread_cid=" + thread_cid
				+ ", ctime=" + ctime
				+ ", content=" + content
				+ ", name=" + name
				+ ", uid=" + uid
				+ ", logo50=" + logo50
				+ ", online=" + online
				+ ", hidden=" + hidden
				+ ", replys=" + replys
				+ '}';
	}
	
	
	public long getThread_cid()
	{
		return thread_cid;
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
	
	public CommentReply[] getReplys()
	{
		return replys;
	}
    
    public static CommentModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new CommentModel[0];
    	}
    	int len = jsons.length();
    	CommentModel[] objs = new CommentModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new CommentModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}