
package kx2_4j.model.records;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RecordVideo {
	private String name;		//视频名称
	private String swf;		//视频swf地址
	private String link;		//视频连接
	private String img;		//视频图片地址
	
    public RecordVideo(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		name = json.getString("name");
        		swf = json.getString("swf");
				link = json.getString("link");
				img = json.getString("img");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RecordVideo(JSONObject json) throws KxException {
        super();
        init(json);
    }
	
	public RecordVideo() {
		// TODO Auto-generated constructor stub
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
        RecordVideo other = (RecordVideo) obj;
        if (link != other.link) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "name=" + name
				+ ", swf=" + swf
				+ ", link=" + link
				+ ", img=" + img
				+ '}';
	}
	
	
	public String getName()
	{
		return name;
	}
	
	public String getSwf()
	{
		return swf;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public String getImg()
	{
		return img;
	}
    
    public static RecordVideo[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new RecordVideo[0];
    	}
    	int len = jsons.length();
    	RecordVideo[] objs = new RecordVideo[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new RecordVideo(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}