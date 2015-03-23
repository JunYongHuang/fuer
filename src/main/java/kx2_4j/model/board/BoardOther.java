package kx2_4j.model.board;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class BoardOther extends BoardModel {
	private BoardOwner boardowner;		//好友
	
    public BoardOther(Response res) throws KxException {
        super(res);
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		boardowner = new BoardOwner(json.getJSONObject("boardowner"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public BoardOther(JSONObject json) throws KxException {
        super(json);
        init(json);
    }

    public BoardOther(){
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "boardowner=" + boardowner
				+ super.toString()
				+ '}';
	}
	
	
	public BoardOwner getBoardowner()
	{
		return boardowner;
	}
    
    public static BoardOther[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new BoardOther[0];
    	}
    	int len = jsons.length();
    	BoardOther[] objs = new BoardOther[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new BoardOther(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}