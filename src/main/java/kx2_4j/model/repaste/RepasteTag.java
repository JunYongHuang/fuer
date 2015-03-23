package kx2_4j.model.repaste;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RepasteTag {
	private long id;		//Tagid
	private String name;		//Tag名称
	private long num;		//该Tag被选中的次数
	private String percentage;		//该Tag的数量占总Tag的百分比
	
    public RepasteTag(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		id = json.getLong("id");
				name = json.getString("name");
				num = json.getLong("num");
				percentage = json.getString("percentage");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RepasteTag(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public RepasteTag(){
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
        RepasteTag other = (RepasteTag) obj;
        
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
				+ ", name=" + name
				+ ", num=" + num
				+ ", percentage=" + percentage
				+ '}';
	}
	
	
	public long getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public long getNum()
	{
		return num;
	}
	
	public String getPercentage()
	{
		return percentage;
	}
	

    
    public static RepasteTag[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new RepasteTag[0];
    	}
    	int len = jsons.length();
    	RepasteTag[] objs = new RepasteTag[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new RepasteTag(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}