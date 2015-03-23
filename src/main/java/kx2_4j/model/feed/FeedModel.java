package kx2_4j.model.feed;


import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedModel {
	private long feed_id;		//动态id
	private FeedPublishedTime published;		//发表时间
	private FeedActor actor;		//动态行为发出者
	private String verb;		//动态简述内容
	private FeedObjectBase object;		//动态内容
	private FeedSource source;		//来源信息
	private long comments;		//评论数
	private long likes;		//赞数
	private long forward;		//转发数
	
    public FeedModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		feed_id = json.getLong("feed_id");
				published = new FeedPublishedTime(json.getJSONObject("published"));
				actor = new FeedActor(json.getJSONObject("actor"));
				verb = json.getString("verb");
				if("uploadphoto".equals(json.getJSONObject("object").getString("objtype")))
				{
					object = new FeedObjectPhoto(json.getJSONObject("object"));
				}
				else
				{
					object = new FeedObject(json.getJSONObject("object"));
				}
				source = new FeedSource(json.getJSONObject("source"));
				comments = json.getLong("comments");
				likes = json.getLong("likes");
				forward = json.getLong("forward");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedModel(){
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
        FeedModel other = (FeedModel) obj;
        
		if (feed_id != other.feed_id) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "feed_id=" + feed_id
				+ ", published=" + published
				+ ", actor=" + actor
				+ ", verb=" + verb
				+ ", object=" + object
				+ ", source=" + source
				+ ", comments=" + comments
				+ ", likes=" + likes
				+ ", forward=" + forward
				+ '}';
	}
	
	
	public long getFeed_id()
	{
		return feed_id;
	}
	
	public FeedPublishedTime getPublished()
	{
		return published;
	}
	
	public FeedActor getActor()
	{
		return actor;
	}
	
	public String getVerb()
	{
		return verb;
	}
	
	public FeedObjectBase getObject()
	{
		return object;
	}
	
	public FeedSource getSource()
	{
		return source;
	}
	
	public long getComments()
	{
		return comments;
	}
	
	public long getLikes()
	{
		return likes;
	}
	
	public long getForward()
	{
		return forward;
	}

    public static FeedModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new FeedModel[0];
    	}
    	int len = jsons.length();
    	FeedModel[] objs = new FeedModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new FeedModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}