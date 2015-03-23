package kx2_4j.model.gift;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GiftRev {
	private long gid;		//礼物id
	private String pic;		//礼物图片链接
	private String time;		//赠送时间
	private String word;		//赠送附言
	private int anonymous;		//是否匿名赠送（0不是，1是）
	private int invisible;		//是否悄悄赠送（0不是，1是）
	private String gname;		//礼物名字
	private long uid;		//礼物赠送者uid/礼物接受者的uid
	private String name;		//礼物赠送者name/礼物接受者的name
	
    public GiftRev(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		gid = json.getLong("gid");
				pic = json.getString("pic");
				time = json.getString("time");
				word = json.getString("word");
				anonymous = json.getInt("anonymous");
				invisible = json.getInt("invisible");
				gname = json.getString("gname");
				uid = json.getLong("uid");
				name = json.getString("name");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GiftRev(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public GiftRev(){
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
        GiftRev other = (GiftRev) obj;
        
		if (gid != other.gid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "gid=" + gid
				+ ", pic=" + pic
				+ ", time=" + time
				+ ", word=" + word
				+ ", anonymous=" + anonymous
				+ ", invisible=" + invisible
				+ ", gname=" + gname
				+ ", uid=" + uid
				+ ", name=" + name
				+ '}';
	}
	
	
	public long getGid()
	{
		return gid;
	}
	
	public String getPic()
	{
		return pic;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public int getAnonymous()
	{
		return anonymous;
	}
	
	public int getInvisible()
	{
		return invisible;
	}
	
	public String getGname()
	{
		return gname;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public String getName()
	{
		return name;
	}
    
    public static GiftRev[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GiftRev[0];
    	}
    	int len = jsons.length();
    	GiftRev[] objs = new GiftRev[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GiftRev(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}