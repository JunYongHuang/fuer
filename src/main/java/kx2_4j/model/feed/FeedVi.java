package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedVi {
	private String url;		//视频源地址
	private String cover;		//视频截图
	private String swf;		//视频播放地址
	
    public FeedVi(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		url = json.getString("url");
				cover = json.getString("cover");
				swf = json.getString("swf");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedVi(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedVi(){
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
        FeedVi other = (FeedVi) obj;
        
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
				+ ", cover=" + cover
				+ ", swf=" + swf
				+ '}';
	}
	
	
	public String getUrl()
	{
		return url;
	}
	
	public String getCover()
	{
		return cover;
	}
	
	public String getSwf()
	{
		return swf;
	}
}