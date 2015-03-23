
package kx2_4j.model.records;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class TopicRecord {
	private String title;		//话题标题
	private String word;		//话题搜索关键词
	private long num;		//话题记录数
	
    public TopicRecord(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		title = json.getString("title");
				word = json.getString("word");
				num = json.getLong("num");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public TopicRecord(JSONObject json) throws KxException {
        super();
        init(json);
    }

	public TopicRecord() {
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
        TopicRecord other = (TopicRecord) obj;
        
		if (title != other.title) {
            return false;
        }
        
		else if (word != other.word) {
            return false;
        }
        
		else if (num != other.num) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "title=" + title
				+ ", word=" + word
				+ ", num=" + num
				+ '}';
	}
	
	
	public String getTitle()
	{
		return title;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public long getNum()
	{
		return num;
	}

    
    public static TopicRecord[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new TopicRecord[0];
    	}
    	int len = jsons.length();
    	TopicRecord[] objs = new TopicRecord[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new TopicRecord(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}