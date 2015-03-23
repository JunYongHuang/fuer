
package kx2_4j.model.records;


import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RecordUser {
	private long uid;		//用户ID
	private String name;		//名称
	private int gender;		//性别
	private String logo50;		//50*50的用户头像
	
    public RecordUser(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				name = json.getString("name");
				gender = json.getInt("gender");
				logo50 = json.getString("logo50");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RecordUser(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public RecordUser(){
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
        RecordUser other = (RecordUser) obj;
        
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
				+ ", gender=" + gender
				+ ", logo50=" + logo50
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
	
	public int getGender()
	{
		return gender;
	}
	
	public String getLogo50()
	{
		return logo50;
	}
}