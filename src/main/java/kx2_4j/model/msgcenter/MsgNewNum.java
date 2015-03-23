package kx2_4j.model.msgcenter;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class MsgNewNum {
	private int message;		//短消息
	private int sysmsg_notice;		//通知
	private int sysmsg_friends;		//好友请求
	private int sysmsg_birthday;		//生日提醒
	private int sysmsg_mention;		//提到我
	private int sysmsg_forward;		//转发
	private int bbs_msg;		//留言
	private int bbs_reply;		//留言回复
	private int rgroup_msg;		//圈子“回复我”
	private int rgroup_reply;		//圈子“我回复”
	private int comment;		//评论
	private int reply;		//评论回复
	
    public MsgNewNum(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		message = json.getInt("message");
				sysmsg_notice = json.getInt("sysmsg_notice");
				sysmsg_friends = json.getInt("sysmsg_friends");
				sysmsg_birthday = json.getInt("sysmsg_birthday");
				sysmsg_mention = json.getInt("sysmsg_mention");
				sysmsg_forward = json.getInt("sysmsg_forward");
				bbs_msg = json.getInt("bbs_msg");
				bbs_reply = json.getInt("bbs_reply");
				rgroup_msg = json.getInt("rgroup_msg");
				rgroup_reply = json.getInt("rgroup_reply");
				comment = json.getInt("comment");
				reply = json.getInt("reply");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public MsgNewNum(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public MsgNewNum(){
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
        MsgNewNum other = (MsgNewNum) obj;
        
		if (message != other.message) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "message=" + message
				+ ", sysmsg_notice=" + sysmsg_notice
				+ ", sysmsg_friends=" + sysmsg_friends
				+ ", sysmsg_birthday=" + sysmsg_birthday
				+ ", sysmsg_mention=" + sysmsg_mention
				+ ", sysmsg_forward=" + sysmsg_forward
				+ ", bbs_msg=" + bbs_msg
				+ ", bbs_reply=" + bbs_reply
				+ ", rgroup_msg=" + rgroup_msg
				+ ", rgroup_reply=" + rgroup_reply
				+ ", comment=" + comment
				+ ", reply=" + reply
				+ '}';
	}
	
	
	public int getMessage()
	{
		return message;
	}
	
	public int getSysmsg_notice()
	{
		return sysmsg_notice;
	}
	
	public int getSysmsg_friends()
	{
		return sysmsg_friends;
	}
	
	public int getSysmsg_birthday()
	{
		return sysmsg_birthday;
	}
	
	public int getSysmsg_mention()
	{
		return sysmsg_mention;
	}
	
	public int getSysmsg_forward()
	{
		return sysmsg_forward;
	}
	
	public int getBbs_msg()
	{
		return bbs_msg;
	}
	
	public int getBbs_reply()
	{
		return bbs_reply;
	}
	
	public int getRgroup_msg()
	{
		return rgroup_msg;
	}
	
	public int getRgroup_reply()
	{
		return rgroup_reply;
	}
	
	public int getComment()
	{
		return comment;
	}
	
	public int getReply()
	{
		return reply;
	}
	

    
    public static MsgNewNum[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new MsgNewNum[0];
    	}
    	int len = jsons.length();
    	MsgNewNum[] objs = new MsgNewNum[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new MsgNewNum(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}