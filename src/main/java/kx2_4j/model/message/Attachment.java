
package kx2_4j.model.message;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class Attachment {
	private long dirid;		//网盘ID
	private long fileid;		//文件ID
	private String name;		//文件名称
	
    public Attachment(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		dirid = json.getLong("dirid");
				fileid = json.getLong("fileid");
				name = json.getString("name");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public Attachment(JSONObject json) throws KxException {
        super();
        init(json);
    }

	public Attachment() {
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
        Attachment other = (Attachment) obj;
        
		if (dirid != other.dirid) {
            return false;
        }
        
		else if (fileid != other.fileid) {
            return false;
        }
        
		else if (name != other.name) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "dirid=" + dirid
				+ ", fileid=" + fileid
				+ ", name=" + name
				+ '}';
	}
	
	
	public long getDirid()
	{
		return dirid;
	}
	
	public long getFileid()
	{
		return fileid;
	}
	
	public String getName()
	{
		return name;
	}
	
    public static Attachment[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new Attachment[0];
    	}
    	int len = jsons.length();
    	Attachment[] objs = new Attachment[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new Attachment(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}