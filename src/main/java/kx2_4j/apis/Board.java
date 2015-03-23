package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Board extends KxAPIcaller {

    public Board(String token)
    {
    	super(token);
    }
    
	/**
	 * 给用户留言
	 * @param uid 留言对象的uid 
	 * @param content 留言内容 
	 * @param secret 是否悄悄话，默认不是悄悄话(0：不是悄悄话；1：悄悄话) 
	 * @return 留言的ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response createBoard(long uid, String content, int secret) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("content", content));
		params.add(new PostParameter("secret", secret));
	    return post("board/create", params);
	}
	/**
	 * 获取我留言板
	 * @param start 起始值 
	 * @param num 获取数目 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getBoard(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "board/me", params);
		}
	/**
	 * 获取用户留言板
	 * @param uid 用户uid 
	 * @param start 起始值 
	 * @param num 获取数目 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getBoardByUid(long uid, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "board/user", params);
		}
	/**
	 * 获取留言回复
	 * @param start 起始值 
	 * @param num 获取数目 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getBoardByMe(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "board/reply_list", params);
		}
	/**
	 * 删除留言
	 * @param uid 评论主人id，只是删除当前用户发表的留言 
	 * @param bid 留言id（bid）或留言主线id（thread_bid） 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response deleteBoard(long uid, long bid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("bid", bid));
	    return get( "board/delete", params);
	}
}