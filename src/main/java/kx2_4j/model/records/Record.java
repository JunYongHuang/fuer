
package kx2_4j.model.records;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class Record {
	private String rid;		//记录ID
	private long ctime;		//创建时间
	private RecordMain main;		//内容信息
	private RecordUser user;		//用户信息
	private String source;		//原记录
	private int rnum;		//转发数
	private int cnum;		//评论数
	
    public Record(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		rid = json.getString("rid");
				ctime = json.getLong("ctime");
				main = new RecordMain(json.getJSONObject("main"));
				user = new RecordUser(json.getJSONObject("user"));
				source = json.getString("source");
				rnum = json.getInt("rnum");
				cnum = json.getInt("cnum");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public Record(JSONObject json) throws KxException {
        super();
        init(json);
    }
    
	public Record() {
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
        Record other = (Record) obj;
		if (rid != other.rid) {
            return false;
        }
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "rid=" + rid
				+ ", ctime=" + ctime
				+ ", main=" + main.toString()
				+ ", user=" + user.toString()
				+ ", source=" + source
				+ ", rnum=" + rnum
				+ ", cnum=" + cnum
				+ '}';
	}
	
	
	public String getRid()
	{
		return rid;
	}
	
	public long getCtime()
	{
		return ctime;
	}
	
	public RecordMain getMain()
	{
		return main;
	}
	
	public RecordUser getUser()
	{
		return user;
	}
	
	public String getSource()
	{
		return source;
	}
	
	public int getRnum()
	{
		return rnum;
	}
	
	public int getCnum()
	{
		return cnum;
	}
    
    public static Record[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new Record[0];
    	}
    	int len = jsons.length();
    	Record[] objs = new Record[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new Record(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}