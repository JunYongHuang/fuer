
package kx2_4j.model.app;


import java.util.ArrayList;
import java.util.List;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class AppUids {
	private List<Long> uids;		//用户ID数组
	private int prev;		//前一分页的起始序号
	private int next;		//后一分页的起始序号
	
    public AppUids(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		if(!json.isNull("uids")){
	        		JSONArray jsona= json.getJSONArray("uids");
	        		int size=jsona.length();
	        		uids =new ArrayList<Long>(size);
	        		for (int i = 0; i < size; i++) {
	        			uids.add(jsona.getLong(i));
					}
	        	}
				prev = json.getInt("prev");
				next = json.getInt("next");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public AppUids(JSONObject json) throws KxException {
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
        AppUids other = (AppUids) obj;
        
		if (uids != other.uids) {
            return false;
        }
        
		else if (prev != other.prev) {
            return false;
        }
        
		else if (next != other.next) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "uids=" + uids
				+ ", prev=" + prev
				+ ", next=" + next
				+ '}';
	}
	
	
	public List<Long> getUids()
	{
		return uids;
	}
	
	public int getPrev()
	{
		return prev;
	}
	
	public int getNext()
	{
		return next;
	}
}