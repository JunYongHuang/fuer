package kx2_4j.model.board;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class BoardModel {
	private long thread_bid;		//留言主线ID
	private String logo50;		//50×50头像
	private String uid;		//发留言用户的UID
	private String name;		//发留言用户姓名
	private String ctime;		//发留言时间
	private String content;		//所发留言的内容
	private int hidden;		//是否为悄悄话（0，不是悄悄话；1，悄悄话）
	private BoardReply[] replys;		//回复
	
    public BoardModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		thread_bid = json.getLong("thread_bid");
				logo50 = json.getString("logo50");
				uid = json.getString("uid");
				name = json.getString("name");
				ctime = json.getString("ctime");
				content = json.getString("content");
				hidden = json.getInt("hidden");
				replys = BoardReply.jsons2objs(json.getJSONArray("replys"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public BoardModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public BoardModel(){
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
        BoardModel other = (BoardModel) obj;
        
		if (thread_bid != other.thread_bid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "thread_bid=" + thread_bid
				+ ", logo50=" + logo50
				+ ", uid=" + uid
				+ ", name=" + name
				+ ", ctime=" + ctime
				+ ", content=" + content
				+ ", hidden=" + hidden
				+ ", replys=" + replys
				+ '}';
	}
	
	
	public long getThread_bid()
	{
		return thread_bid;
	}
	
	public String getLogo50()
	{
		return logo50;
	}
	
	public String getUid()
	{
		return uid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public int getHidden()
	{
		return hidden;
	}
	
	public BoardReply[] getReplys()
	{
		return replys;
	}
    
    public static BoardModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new BoardModel[0];
    	}
    	int len = jsons.length();
    	BoardModel[] objs = new BoardModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new BoardModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}