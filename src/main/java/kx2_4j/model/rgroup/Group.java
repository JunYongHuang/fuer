package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class Group {
	private long gid;		//圈子ID
	private String name;		//圈子名称
	private long ctime;		//圈子创建时间
	private int new_talk;		//未读圈子记录数
	private int catalog;		//圈子分类 0/1/2/3/4/5/6/7 默认/同学/家人/同事/饭友/玩友/闺蜜/其他
	
    public Group(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		gid = json.getLong("gid");
				name = json.getString("name");
				ctime = json.getLong("ctime");
				new_talk = json.getInt("new_talk");
				catalog = json.getInt("catalog");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public Group(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public Group(){
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
        Group other = (Group) obj;
        
		if (gid != other.gid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "gid=" + gid
				+ ", name=" + name
				+ ", ctime=" + ctime
				+ ", new_talk=" + new_talk
				+ ", catalog=" + catalog
				+ '}';
	}
	
	
	public long getGid()
	{
		return gid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public long getCtime()
	{
		return ctime;
	}
	
	public int getNew_talk()
	{
		return new_talk;
	}
	
	public int getCatalog()
	{
		return catalog;
	}
    
    public static Group[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new Group[0];
    	}
    	int len = jsons.length();
    	Group[] objs = new Group[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new Group(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}