
package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GroupNew {
	private long stid;		//原始动态id
	private long tid;		//动态id
	private long ctime;		//创建时间
	private int talk_type;		//动态类型。0：默认动态，2：邀请动态，3：创建动态
	private RgroupUser user;		//用户信息
	private GroupNewMain[] main;		//动态
	private int replynum;		//回复数量
	private GroupNewPic pic;		//图片信息
	
    public GroupNew(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		stid = json.getLong("stid");
				tid = json.getLong("tid");
				ctime = json.getLong("ctime");
				talk_type = json.getInt("talk_type");
				user = new RgroupUser(json.getJSONObject("user"));
				replynum = json.getInt("replynum");
				pic = new GroupNewPic(json.getJSONObject("pic"));
				main = GroupNewMain.jsons2objs(json.getJSONArray("main"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GroupNew(JSONObject json) throws KxException {
        super();
        init(json);
    }

	public GroupNew() {
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
        GroupNew other = (GroupNew) obj;
		if (stid != other.stid) {
            return false;
        }
		else if (tid != other.tid) {
            return false;
        }
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "stid=" + stid
				+ ", tid=" + tid
				+ ", ctime=" + ctime
				+ ", talk_type=" + talk_type
				+ ", user=" + user
				+ ", main=" + main
				+ ", replynum=" + replynum
				+ ", pic=" + pic
				+ '}';
	}
	
	
	public long getStid()
	{
		return stid;
	}
	
	public long getTid()
	{
		return tid;
	}
	
	public long getCtime()
	{
		return ctime;
	}
	
	public int getTalk_type()
	{
		return talk_type;
	}
	
	public RgroupUser getUser()
	{
		return user;
	}
	
	public GroupNewMain[] getMain()
	{
		return main;
	}
	
	public int getReplynum()
	{
		return replynum;
	}
	
	public GroupNewPic getPic()
	{
		return pic;
	}
    
    public static GroupNew[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GroupNew[0];
    	}
    	int len = jsons.length();
    	GroupNew[] objs = new GroupNew[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GroupNew(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}