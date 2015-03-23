package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedObjectPhoto extends FeedObjectBase {
	private FeedImage[] image;		//图片信息
	
    public FeedObjectPhoto(Response res) throws KxException {
        super(res);
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		image = FeedImage.jsons2objs(json.getJSONArray("image"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedObjectPhoto(JSONObject json) throws KxException {
        super(json);
        init(json);
    }

    public FeedObjectPhoto(){
    }
	
	
	
	public String toString()
	{
		return '{'
				+super.toString()
				+ "image=" + image
				+ '}';
	}
	
	
	public FeedImage[] getImage()
	{
		return image;
	}
}