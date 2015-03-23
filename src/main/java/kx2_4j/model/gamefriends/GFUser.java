package kx2_4j.model.gamefriends;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GFUser {
	private long id;		//请求ID
	private long uid;		//用户UID
	private int privacy;		//是否可以被找到 1：可以 0：不可以
	private String city;		//城市
	private int gender;		//性别 0：男 1：女
	private String birthday;		//生日
	private String name;		//用户名
	private String logo25;		//头像25px x 25px
	private int level;		//等级
	
    public GFUser(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		id = json.getLong("id");
				uid = json.getLong("uid");
				privacy = json.getInt("privacy");
				city = json.getString("city");
				gender = json.getInt("gender");
				birthday = json.getString("birthday");
				name = json.getString("name");
				logo25 = json.getString("logo25");
				level = json.getInt("level");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GFUser(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public GFUser(){
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
        GFUser other = (GFUser) obj;
        
		if (id != other.id) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "id=" + id
				+ ", uid=" + uid
				+ ", privacy=" + privacy
				+ ", city=" + city
				+ ", gender=" + gender
				+ ", birthday=" + birthday
				+ ", name=" + name
				+ ", logo25=" + logo25
				+ ", level=" + level
				+ '}';
	}
	
	
	public long getId()
	{
		return id;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public int getPrivacy()
	{
		return privacy;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public int getGender()
	{
		return gender;
	}
	
	public String getBirthday()
	{
		return birthday;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getLogo25()
	{
		return logo25;
	}
	
	public int getLevel()
	{
		return level;
	}
	
    public static GFUser[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GFUser[0];
    	}
    	int len = jsons.length();
    	GFUser[] objs = new GFUser[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GFUser(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}