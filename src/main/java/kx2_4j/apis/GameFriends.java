package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class GameFriends extends KxAPIcaller {

    public GameFriends(String token)
    {
    	super(token);
    }
    
	/**
	 * 将当前用户创建为当前应用的玩友
	 
	 * @return 创建玩友返回的状态 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response createGameFriend() throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    
	    return post("game_friends/create", params);
	}
	/**
	 * 设置用户是否可以被别人找到
	 * @param privacy 玩友是否可以找到 1：可以 0：不可以 
	 * @return 设置是否可以找到返回的状态 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response setPrivacy(int privacy) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("privacy", privacy));
	    return post("game_friends/change_privacy", params);
	}
	/**
	 * 当前用户向一位用户发送加为玩友的请求
	 * @param touid 要添加为玩友的用户ID 
	 * @return 加玩友返回的状态 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response request(long touid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("touid", touid));
	    return post("game_friends/request", params);
	}
	/**
	 * 圈子列表
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getRequest(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "game_friends/request_list", params);
		}
	/**
	 * 处理玩友请求
	 * @param touid 要添加为玩友的用户ID 
	 * @param accept 是否接受 0：拒绝 1：接受 
	 * @param id 请求ID，为request_list接口返回数据中的id字段值 
	 * @return 玩友请求返回的状态 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response dealRequest(long touid, int accept, long id) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("touid", touid));
		params.add(new PostParameter("accept", accept));
		params.add(new PostParameter("id", id));
	    return post("game_friends/deal_request", params);
	}
	/**
	 * 圈子列表
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getGamefriends(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "game_friends/friends", params);
		}
	/**
	 * 圈子列表
	 * @param city 城市，例子：北京 
	 * @param agespan 年龄区间，可用参数：0,1959 1960,1969 1970,1979 1980,1984 1985,1989 1990 
	 * @param gender 0：男 1：女 
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response searchGamefriends(String city, String agespan, int gender, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("city", city));
		params.add(new PostParameter("agespan", agespan));
		params.add(new PostParameter("gender", gender));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "game_friends/search", params);
		}
	/**
	 * 圈子列表
	 * @param uids 用户ID，可以设置多个，以半角逗号分隔 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getGFByUids(String uids) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uids", uids));
		return get( "game_friends/show", params);
		}
}