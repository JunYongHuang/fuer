package kx2_4j.model.comment;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class CommentWrap extends CommentModel {
	private CommentSource source;		//被评论资源
	
    public CommentWrap(Response res) throws KxException, JSONException {
        super(res);
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		source = new CommentSource(json.getJSONObject("source"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public CommentWrap(JSONObject json) throws KxException {
        super(json);
        init(json);
    }

    public CommentWrap(){
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "source=" + source
				+ ',' + super.toString()
				+ '}';
	}
	
	
	public CommentSource getSource()
	{
		return source;
	}
    
    public static CommentWrap[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new CommentWrap[0];
    	}
    	int len = jsons.length();
    	CommentWrap[] objs = new CommentWrap[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new CommentWrap(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}