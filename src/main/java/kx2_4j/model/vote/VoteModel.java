package kx2_4j.model.vote;


import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.records.RecordUser;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class VoteModel {
	private long vid;		//投票ID
	private RecordUser userinfo;		//用户信息
	private String topic;		//投票标题
	private long votenum;		//投票数
	private String ctime;		//创建时间
	private String endtime;		//投票结束时间
	private String intro;		//投票简介
	private int maxnum;		//最大投票数
	private long commentnum;		//评论数
	private int glimit;		//性别限制 0：没限制 1：限制女性 2：限制男性
	private String mtime;		//最后修改时间
	private int onlyfriend;		//是否只能由好友投票
	private VoteAnswer[] answers;		//投票选项
	
    public VoteModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		vid = json.getLong("vid");
				userinfo = new RecordUser(json.getJSONObject("userinfo"));
				topic = json.getString("topic");
				votenum = json.getLong("votenum");
				ctime = json.getString("ctime");
				endtime = json.getString("endtime");
				intro = json.getString("intro");
				maxnum = json.getInt("maxnum");
				commentnum = json.getLong("commentnum");
				glimit = json.getInt("glimit");
				mtime = json.getString("mtime");
				onlyfriend = json.getInt("onlyfriend");
				String answer = json.getString("answers");
				if(answer.startsWith("{"))
				{
					JSONObject list = json.getJSONObject("answers");
					answers = new VoteAnswer[list.length()];
					String[] ites = JSONObject.getNames(list);
					for(int i=0;i<ites.length;i++)
		 		    {
		 			    answers[i] = new VoteAnswer(list,ites[i]);
		 		    }
				}
				if(answer.startsWith("["))
				{
					JSONArray list = json.getJSONArray("answers");
					answers = new VoteAnswer[list.length()];
					for(int i=0;i<list.length();i++)
		 		    {
		 			    answers[i] = new VoteAnswer(list,i);
		 		    }
				}
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public VoteModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public VoteModel(){
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
        VoteModel other = (VoteModel) obj;
        
		if (vid != other.vid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "vid=" + vid
				+ ", userinfo=" + userinfo
				+ ", topic=" + topic
				+ ", votenum=" + votenum
				+ ", ctime=" + ctime
				+ ", endtime=" + endtime
				+ ", intro=" + intro
				+ ", maxnum=" + maxnum
				+ ", commentnum=" + commentnum
				+ ", glimit=" + glimit
				+ ", mtime=" + mtime
				+ ", onlyfriend=" + onlyfriend
				+ ", answers=" + answers
				+ '}';
	}
	
	
	public long getVid()
	{
		return vid;
	}
	
	public RecordUser getUserinfo()
	{
		return userinfo;
	}
	
	public String getTopic()
	{
		return topic;
	}
	
	public long getVotenum()
	{
		return votenum;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	
	public String getEndtime()
	{
		return endtime;
	}
	
	public String getIntro()
	{
		return intro;
	}
	
	public int getMaxnum()
	{
		return maxnum;
	}
	
	public long getCommentnum()
	{
		return commentnum;
	}
	
	public int getGlimit()
	{
		return glimit;
	}
	
	public String getMtime()
	{
		return mtime;
	}
	
	public int getOnlyfriend()
	{
		return onlyfriend;
	}
	
	public VoteAnswer[] getAnswers()
	{
		return answers;
	}
    
    public static VoteModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new VoteModel[0];
    	}
    	int len = jsons.length();
    	VoteModel[] objs = new VoteModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new VoteModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}