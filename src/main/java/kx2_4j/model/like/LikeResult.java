package kx2_4j.model.like;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class LikeResult {
	private int result;		//取消赞是否成功 0:失败 1:成功
	
    public LikeResult(Response res) throws KxException, JSONException {
        super();
        init(res.asJSONObject().getJSONObject("data"));
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		result = json.getInt("result");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public LikeResult(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public LikeResult(){
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
        LikeResult other = (LikeResult) obj;
        
		if (result != other.result) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "result=" + result
				+ '}';
	}
	
	
	public int getResult()
	{
		return result;
	}
}