package kx2_4j.model.repaste;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RepasteDetail {
	private long first_uid;		//第一转贴人的uid
	private String first_name;		//第一转贴人的姓名
	private String title;		//转贴标题
	private String ctime;		//转贴创建时间
	private long repaste_total;		//累计转贴次数
	private long view_total;		//累计浏览次数
	private long interact;		//互动次数
	private String content;		//转贴内容
	private String content_video;		//视频内容(转贴含有视频时, 此参数显示)
	private int usernum;		//
	private RepasteTagUser[] taged_friends;		//Tag类型的用户信息
	private int hastaged;		//是否选择了标签
	private RepasteTag[] tags;		//转贴的TAG列表
	private RepasteVoteUser[] voted_users;		//投票类型的用户信息
	private long votenum;		//投票总数
	private int hasvoted;		//是否已经投票
	private RepasteVote[] votes;		//投票信息
	
    public RepasteDetail(Response res) throws KxException, JSONException {
        super();
        init(res.asJSONObject().getJSONObject("data"));
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		first_uid = json.getLong("first_uid");
				first_name = json.getString("first_name");
				title = json.getString("title");
				ctime = json.getString("ctime");
				repaste_total = json.getLong("repaste_total");
				view_total = json.getLong("view_total");
				interact = json.getLong("interact");
				content = json.getString("content");
				content_video = json.getString("content_video");
				usernum = json.getInt("usernum");
				hastaged = json.getInt("hastaged");
				votenum = json.getLong("votenum");
				hasvoted = json.getInt("hasvoted");
				tags = RepasteTag.jsons2objs(json.getJSONArray("tags"));
				voted_users = RepasteVoteUser.jsons2objs(json.getJSONArray("voted_users"));
				taged_friends = RepasteTagUser.jsons2objs(json.getJSONArray("taged_friends"));
				votes = RepasteVote.jsons2objs(json.getJSONArray("votes"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RepasteDetail(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public RepasteDetail(){
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
        RepasteDetail other = (RepasteDetail) obj;
        
		if (first_name != other.first_name) {
            return false;
        }
        
		else if (title != other.title) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "first_uid=" + first_uid
				+ ", first_name=" + first_name
				+ ", title=" + title
				+ ", ctime=" + ctime
				+ ", repaste_total=" + repaste_total
				+ ", view_total=" + view_total
				+ ", interact=" + interact
				+ ", content=" + content
				+ ", content_video=" + content_video
				+ ", usernum=" + usernum
				+ ", taged_friends=" + taged_friends
				+ ", hastaged=" + hastaged
				+ ", tags=" + tags
				+ ", voted_users=" + voted_users
				+ ", votenum=" + votenum
				+ ", hasvoted=" + hasvoted
				+ ", votes=" + votes
				+ '}';
	}
	
	
	public long getFirst_uid()
	{
		return first_uid;
	}
	
	public String getFirst_name()
	{
		return first_name;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	
	public long getRepaste_total()
	{
		return repaste_total;
	}
	
	public long getView_total()
	{
		return view_total;
	}
	
	public long getInteract()
	{
		return interact;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getContent_video()
	{
		return content_video;
	}
	
	public int getUsernum()
	{
		return usernum;
	}

	public RepasteTagUser[] getTaged_friends()
	{
		return taged_friends;
	}
	
	public int getHastaged()
	{
		return hastaged;
	}
	
	public RepasteTag[] getTags()
	{
		return tags;
	}
	
	public RepasteVoteUser[] getVoted_ursers()
	{
		return voted_users;
	}
	
	public long getVotenum()
	{
		return votenum;
	}
	
	public int getHasvoted()
	{
		return hasvoted;
	}
	
	public RepasteVote[] getVotes()
	{
		return votes;
	}
}