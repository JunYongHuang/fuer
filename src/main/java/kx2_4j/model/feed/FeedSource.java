package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedSource {
	private String title;		//来源名称
	private String url;		//来源链接
	
    public FeedSource(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		title = json.getString("title");
				url = json.getString("url");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedSource(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedSource(){
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
        FeedSource other = (FeedSource) obj;
        
		if (title != other.title) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "title=" + title
				+ ", url=" + url
				+ '}';
	}
	
	
	public String getTitle()
	{
		return title;
	}
	
	public String getUrl()
	{
		return url;
	}
}