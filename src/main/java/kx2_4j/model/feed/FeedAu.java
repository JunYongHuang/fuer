package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedAu {
	private String url;		//音频源地址
	private String name;		//音频名字
	
    public FeedAu(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		url = json.getString("url");
        		name = json.getString("name");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedAu(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedAu(){
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
        FeedAu other = (FeedAu) obj;
        
		if (url != other.url) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "url=" + url
				+ "name=" + name
				+ '}';
	}
	
	
	public String getUrl()
	{
		return url;
	}

	public String getName()
	{
		return name;
	}
}