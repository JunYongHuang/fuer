package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedObject extends FeedObjectBase {
	private FeedPic image;		//图片信息
	
    public FeedObject(Response res) throws KxException {
        super(res);
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		if(json.getString("image").length()>0)
        		image = new FeedPic(json.getJSONObject("image"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedObject(JSONObject json) throws KxException {
        super(json);
        init(json);
    }

    public FeedObject(){
    }
	
	
	
	@Override
	public String toString()
	{
		return '{'
				+ super.toString()
				+ " image=" + image
				+ '}';
	}
	
	
	public FeedPic getImage()
	{
		return image;
	}
}