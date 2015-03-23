package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedDescription {
	private String content;		//动态内容摘要
	
    public FeedDescription(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		content = json.getString("content");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedDescription(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedDescription(){
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
        FeedDescription other = (FeedDescription) obj;
        
		if (content != other.content) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "content=" + content
				+ '}';
	}
	
	
	public String getContent()
	{
		return content;
	}
}