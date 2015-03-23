package kx2_4j.model.lbs;


import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class LBS {
	private long uid;		//用户UID
	private String name;		//用户名/地点名称
	private String logo50;		//50*50头像
	private Location[] lbslist;		//签到列表
	
    public LBS(Response res) throws KxException, JSONException {
        super();
        init(res.asJSONObject().getJSONObject("lbslist"));
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				name = json.getString("name");
				logo50 = json.getString("logo50");
				int size = json.length() - 3;
				lbslist = new Location[size];
				for(int i=0;i<size;i++)
				{
					lbslist[i] =  new Location(json.getJSONObject(String.valueOf(i)));
				}
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public LBS(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public LBS(){
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
        LBS other = (LBS) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", name=" + name
				+ ", logo50=" + logo50
				+ ", lbslist=" + lbslist
				+ '}';
	}
	
	
	public long getUid()
	{
		return uid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getLogo50()
	{
		return logo50;
	}
	
	public Location[] getLbslist()
	{
		return lbslist;
	}
}