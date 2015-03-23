package kx2_4j.model.feed;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FeedMedia {
	private FeedVi vi;		//视频
	private FeedAu au;		//音频
	
    public FeedMedia(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		vi = new FeedVi(json.getJSONObject("vi"));
				au = new FeedAu(json.getJSONObject("au"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FeedMedia(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FeedMedia(){
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
        FeedMedia other = (FeedMedia) obj;
        
		if (vi != other.vi) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "vi=" + vi
				+ ", au=" + au
				+ '}';
	}
	
	
	public FeedVi getVi()
	{
		return vi;
	}
	
	public FeedAu getAu()
	{
		return au;
	}
}