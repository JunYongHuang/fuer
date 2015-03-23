package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedObjectBase {
	private long appid;		//动态组件id
	private String objtype;		//动态资源类型
	private String objid;		//对象ID
	private String title;		//资源标题
	private FeedDescription description;		//动态内容描述
	private FeedLbs lbs;		//位置信息
	private FeedMedia media;	//多媒体信息
	
    public FeedObjectBase(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		appid = json.getLong("appid");
				objtype = json.getString("objtype");
				objid = json.getString("objid");
				title = json.getString("title");
				description = new FeedDescription(json.getJSONObject("description"));
				lbs = new FeedLbs(json.getJSONObject("lbs"));
				if(json.getString("media").length() > 0){
					media = new FeedMedia(json.getJSONObject("media"));
				}
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedObjectBase(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedObjectBase(){
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
        FeedObjectBase other = (FeedObjectBase) obj;
        
		if (appid != other.appid) {
            return false;
        }
        
		else if (objid != other.objid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "appid=" + appid
				+ ", objtype=" + objtype
				+ ", objid=" + objid
				+ ", title=" + title
				+ ", description=" + description
				+ ", lbs=" + lbs
				+ '}';
	}
	
	
	public long getAppid()
	{
		return appid;
	}
	
	public String getObjtype()
	{
		return objtype;
	}
	
	public String getObjid()
	{
		return objid;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public FeedDescription getDescription()
	{
		return description;
	}
	
	public FeedLbs getLbs()
	{
		return lbs;
	}
	
	public FeedMedia getMedia()
	{
		return media;
	}
}