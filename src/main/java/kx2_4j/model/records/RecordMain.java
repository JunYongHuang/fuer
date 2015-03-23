
package kx2_4j.model.records;


import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RecordMain {
	private String content;		//内容
	private RecordVideo[] videos;		//视频内容
	private String[] pics;		//图片信息
	
    public RecordMain(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		content = json.getString("content");
				if(!json.isNull("pics")){
	        		JSONArray jsona = json.getJSONArray("pics");
	        		int size = jsona.length();
	        		pics = new String[size];
	        		for (int i = 0; i < size; i++) {
	        			pics[i] = jsona.getJSONObject(i).getString("src");
					}
	        	}
				videos = RecordVideo.jsons2objs(json.getJSONArray("videos"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RecordMain(JSONObject json) throws KxException {
        super();
        init(json);
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
        RecordMain other = (RecordMain) obj;
        
		if (content != other.content) {
            return false;
        }
        
		else if (videos != other.videos) {
            return false;
        }
        
		else if (pics != other.pics) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "content=" + content
				+ ", videos=" + videos
				+ ", pics=" + pics
				+ '}';
	}
	
	
	public String getContent()
	{
		return content;
	}
	
	public RecordVideo[] getVideos()
	{
		return videos;
	}
	
	public String[] getPics()
	{
		return pics;
	}
}