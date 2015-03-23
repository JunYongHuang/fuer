package kx2_4j.model.board;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class BoardReply {
	private long bid;		//留言id
	private String logo25;		//25×25头像
	private long uid;		//发留言用户的UID
	private String name;		//发留言用户姓名
	private String ctime;		//发留言时间
	private String content;		//所发留言的内容
	private int hidden;		//是否为悄悄话（0，不是悄悄话；1，悄悄话）
	
    public BoardReply(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		bid = json.getLong("bid");
				logo25 = json.getString("logo25");
				uid = json.getLong("uid");
				name = json.getString("name");
				ctime = json.getString("ctime");
				content = json.getString("content");
				hidden = json.getInt("hidden");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public BoardReply(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public BoardReply(){
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
        BoardReply other = (BoardReply) obj;
        
		if (bid != other.bid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "bid=" + bid
				+ ", logo25=" + logo25
				+ ", uid=" + uid
				+ ", name=" + name
				+ ", ctime=" + ctime
				+ ", content=" + content
				+ ", hidden=" + hidden
				+ '}';
	}
	
	
	public long getBid()
	{
		return bid;
	}
	
	public String getLogo25()
	{
		return logo25;
	}
	
	public long getUid()
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
    
    public static BoardReply[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new BoardReply[0];
    	}
    	int len = jsons.length();
    	BoardReply[] objs = new BoardReply[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new BoardReply(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}