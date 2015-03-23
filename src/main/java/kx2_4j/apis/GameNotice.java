package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class GameNotice extends KxAPIcaller {

    public GameNotice(String token)
    {
    	super(token);
    }
    
	/**
	 * 设置未阅提醒
	 * @param uid 未阅游戏通知需要被重置用户的uid 
	 * @param num 未阅游戏通知需要被重置的数目 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response setNoticeNum(long uid, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("num", num));
	    return post("gamenotice/set", params);
	}
	/**
	 * 更新未阅提醒
	 * @param uid 未阅游戏通知需要被重置用户的uid 
	 * @param num 未阅游戏通知需要被变更的数目 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response updateNoticeNum(long uid, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("num", num));
	    return post("gamenotice/update", params);
	}
}