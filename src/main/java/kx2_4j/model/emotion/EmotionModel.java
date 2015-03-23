
package kx2_4j.model.emotion;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class EmotionModel {
	private String emotion;		//表情内容
	private String title;		//表情标题
	private String src;		//表情图片地址
	private String type;		//表情类型：state或者face
	
    public EmotionModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		emotion = json.getString("emotion");
				title = json.getString("title");
				src = json.getString("src");
				type = json.getString("type");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public EmotionModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

	public EmotionModel() {
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
        EmotionModel other = (EmotionModel) obj;
		if (emotion != other.emotion) {
            return false;
        }
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "emotion=" + emotion
				+ ", title=" + title
				+ ", src=" + src
				+ ", type=" + type
				+ '}';
	}
	
	
	public String getEmotion()
	{
		return emotion;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getSrc()
	{
		return src;
	}
	
	public String getType()
	{
		return type;
	}
    
    public static EmotionModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new EmotionModel[0];
    	}
    	int len = jsons.length();
    	EmotionModel[] objs = new EmotionModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new EmotionModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}