package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class MsgCenter extends KxAPIcaller {

    public MsgCenter(String token)
    {
    	super(token);
    }
    
	/**
	 * 获取制定类型消息数量
	 * @param msg_type 消息类型（message/sysmsg_notice/sysmsg_friends/sysmsg_birthday/sysmsg_mention/sysmsg_forward/bbs_msg/bbs_reply/rgroup_msg/rgroup_reply/comment/reply, 短消息/通知/好友请求/生日请求/提到我/转发/留言/留言回复/圈子“回复我”/圈子“我回复”/评论/评论回复） 
	 * @return 
	 * @throws KxException
	 * @scope basic
	 */
	public Response getMsgNewNum(String msg_type) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("msg_type", msg_type));
	    return get( "msg/summary", params);
	}
	/**
	 * 清空制定类型的消息数
	 * @param msg_type 消息类型（message/sysmsg_notice/sysmsg_friends/sysmsg_birthday/sysmsg_mention/sysmsg_forward/bbs_msg/bbs_reply/rgroup_msg/rgroup_reply/comment/reply, 短消息/通知/好友请求/生日请求/提到我/转发/留言/留言回复/圈子“回复我”/圈子“我回复”/评论/评论回复） 
	 * @return 0：失败 1：成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response clearMsgNewNum(String msg_type) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("msg_type", msg_type));
	    return post("msg/clear", params);
	}
}