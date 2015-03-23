package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class CommentAC extends KxAPIcaller {

    public CommentAC(String token)
    {
    	super(token);
    }
    
	/**
	 * 添加对某一资源的评论
	 * @param objtype 被评论资源的类型 目前支持的资源有：photo, album, records, diary, repaste
	 * @param objid 被评论资源的ID 
	 * @param ouid 被评论资源所有者的UID 
	 * @param content 评论内容 
	 * @param secret 是否悄悄话 0：不是悄悄话 1：是悄悄话，默认为0
	 * @return 评论回复ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response createComment(String objtype, long objid, long ouid, String content, int secret) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
		params.add(new PostParameter("content", content));
		params.add(new PostParameter("secret", secret));
	    return post("comment/create", params);
	}
	/**
	 * 回复某一资源的评论
	 * @param objtype 被评论资源的类型 目前支持的资源有：photo, album, records, diary, repaste
	 * @param objid 被评论资源的ID 
	 * @param ouid 被评论资源所有者的UID 
	 * @param thread_cid 评论的ID 
	 * @param owner 评论所有者的UID 
	 * @param content 回复内容 
	 * @return 回复评论ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response replyComment(String objtype, long objid, long ouid, long thread_cid, long owner, String content) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
		params.add(new PostParameter("thread_cid", thread_cid));
		params.add(new PostParameter("owner", owner));
		params.add(new PostParameter("content", content));
	    return post("comment/reply", params);
	}
	/**
	 * 获取某一资源的所有评论内容
	 * @param objtype 被评论资源的类型 目前支持的资源有：photo, album, records, diary, repaste
	 * @param objid 被评论资源的ID 
	 * @param ouid 被评论资源所有者的UID 
	 * @param start 起始值 
	 * @param num 返回数量，默认10 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getComments2obj(String objtype, long objid, long ouid, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "comment/list", params);
	}
	/**
	 * 获取别人给我的评论
	 * @param start 起始值 
	 * @param num 返回数量，默认20 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_comment 
	 */
	public Response getComments(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "comment/comment_list", params);
		}
	/**
	 * 获取别人给我的评论
	 * @param start 起始值 
	 * @param num 返回数量，默认10 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_comment 
	 */
	public Response getReplys(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "comment/reply_list", params);
		}
	/**
	 * 删除评论
	 * @param uid 评论资源所有者的uid。只能删除当前用户的评论 
	 * @param cid 评论id（cid）或评论主线id（thread_cid） 
	 * @return result	返回结果 1：成功 0：不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response deleteComment(long uid, long cid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("cid", cid));
	    return get("comment/delete", params);
	}
}