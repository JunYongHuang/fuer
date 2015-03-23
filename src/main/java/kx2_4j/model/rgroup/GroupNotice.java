package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.records.RecordUser;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GroupNotice {
	private long stid;		//原始动态id
	private long ctime;		//创建时间
	private long gid;		//圈子id
	private String gname;		//圈子name
	private int rnum;		//回复数量
	private int talk_type;		//动态类型 0：默认动态 2：邀请动态 3：创建动态
	private RecordUser suserinfo;		//发布原始动态的用户
	private GroupNewMain[] main;		//动态
	private GroupTalkN lastreply;		//最新回复
	
    public GroupNotice(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		stid = json.getLong("stid");
				ctime = json.getLong("ctime");
				gid = json.getLong("gid");
				gname = json.getString("gname");
				rnum = json.getInt("rnum");
				talk_type = json.getInt("talk_type");
				suserinfo = new RecordUser(json.getJSONObject("suserinfo"));
				lastreply = new GroupTalkN(json.getJSONObject("lastreply"));
				main = GroupNewMain.jsons2objs(json.getJSONArray("main"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GroupNotice(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public GroupNotice(){
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
        GroupNotice other = (GroupNotice) obj;
        
		if (lastreply != other.lastreply) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "stid=" + stid
				+ ", ctime=" + ctime
				+ ", gid=" + gid
				+ ", gname=" + gname
				+ ", rnum=" + rnum
				+ ", talk_type=" + talk_type
				+ ", suserinfo=" + suserinfo
				+ ", main=" + main
				+ ", lastreply=" + lastreply
				+ '}';
	}
	
	
	public long getStid()
	{
		return stid;
	}
	
	public long getCtime()
	{
		return ctime;
	}
	
	public long getGid()
	{
		return gid;
	}
	
	public String getGname()
	{
		return gname;
	}
	
	public int getRnum()
	{
		return rnum;
	}
	
	public int getTalk_type()
	{
		return talk_type;
	}
	
	public RecordUser getSuserinfo()
	{
		return suserinfo;
	}
	
	public GroupNewMain[] getMain()
	{
		return main;
	}
	
	public GroupTalkN getLastreply()
	{
		return lastreply;
	}
    
    public static GroupNotice[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GroupNotice[0];
    	}
    	int len = jsons.length();
    	GroupNotice[] objs = new GroupNotice[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GroupNotice(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}