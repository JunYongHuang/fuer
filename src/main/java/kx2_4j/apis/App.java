
package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class App extends KxAPIcaller {

    public App(String token)
    {
    	super(token);
    }
	
	/**
	 * 获取用户安装组件的状态(如果是连接网站，则获取用户授权此应用的状态)
	 * @param uids 用户ID，可以设置多个，以逗号分隔。如：123456,220993, 最多不能超过50个。
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getAppStatus(String uids) throws KxException, JSONException
	{
		if (uids.length() == 0) {
            throw new KxException("uids参数为空");
        }
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
		params.add(new PostParameter("uids",uids));
	    return get("app/status", params);
	}
	
	/**
	 * 获取当前用户安装组件的好友的uid列表(如果是连接网站，则获取当前用户授权此应用的好友uid列表)
	 * @param start 起始搜索(选填，默认0)。
	 * @param num 需要返回记录的个数(选填，默认20，最多50)。
	 * @return
	 * @throws KxException
	 * @scope basic
	 */
	public Response getFriendsInsApp(int start, int num) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "app/friends", params);
	}
	
	/**
	 * 获取某人邀请成功的好友uid列表
	 * @param uid 用户ID。
	 * @param start 起始搜索(选填，默认0)。
	 * @param num 需要返回记录的个数(选填，默认20，最多50)。
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getUsersInvited(long uid, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "app/invited", params);
	}

	/**
	 * 查询当前应用的剩余请求数
	 * @param  
	 * @return
	 * @throws KxException
	 * @scope basic
	 */
	public Response getRate_limit( ) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    return get( "app/rate_limit_status", params);
	}
}