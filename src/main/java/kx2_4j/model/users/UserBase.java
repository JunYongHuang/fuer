package kx2_4j.model.users;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

/**
 * A data class representing Basic user information element
 */
public class UserBase{
    protected long uid;		//开心网uid
    protected String name;	//真实姓名
    protected String logo50;	//50*50头像url地址
    
    public UserBase()
    {
    	
    }
    public UserBase(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
            try {
                uid = json.getLong("uid");
                name = json.getString("name");
                logo50 = json.getString("logo50");
            } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public UserBase(JSONObject json) throws KxException {
        super();
        init(json);
    }
    
    /**
     * Returns the uid of the user
     *
     * @return the uid of the user
     */
    public long getUid() {
        return uid;
    }

    /**
     * Returns the name of the user
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    public String getLogo50() {
        return logo50;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (uid ^ (uid >>> 32));
        return result;
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
        UserBase other = (UserBase) obj;
        if (uid != other.uid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{"
                + "uid=" + uid
                + ", name='" + name + '\''
                + ", logo50='" + logo50 + '\''
                + '}';
    }

    
    public static UserBase[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new UserBase[0];
    	}
    	int len = jsons.length();
    	UserBase[] objs = new UserBase[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new UserBase(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}