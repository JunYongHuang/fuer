package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.records.RecordUser;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GroupTalk {
	private long tid;		//动态id
	private GroupNewMain[] main;		//动态
	private long ctime;		//创建时间
	private RecordUser user;		//用户信息
	private GroupNewPic pic;		//图片信息
	
    public GroupTalk(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		tid = json.getLong("tid");
				ctime = json.getLong("ctime");
				user = new RecordUser(json.getJSONObject("user"));
				if(json.getString("pic").length() > 0)
				{
					pic = new GroupNewPic(json.getJSONObject("pic"));
				}
				main = GroupNewMain.jsons2objs(json.getJSONArray("main"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GroupTalk(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public GroupTalk(){
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
        GroupTalk other = (GroupTalk) obj;
        
		if (tid != other.tid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "tid=" + tid
				+ ", main=" + main
				+ ", ctime=" + ctime
				+ ", user=" + user
				+ ", pic=" + pic
				+ '}';
	}
	
	
	public long getTid()
	{
		return tid;
	}
	
	public GroupNewMain[] getMain()
	{
		return main;
	}
	
	public long getCtime()
	{
		return ctime;
	}
	
	public RecordUser getUser()
	{
		return user;
	}
	
	public GroupNewPic getPic()
	{
		return pic;
	}
    
    public static GroupTalk[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GroupTalk[0];
    	}
    	int len = jsons.length();
    	GroupTalk[] objs = new GroupTalk[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GroupTalk(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}