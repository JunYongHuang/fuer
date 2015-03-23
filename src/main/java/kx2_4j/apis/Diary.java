package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Diary extends KxAPIcaller {

    public Diary(String token)
    {
    	super(token);
    }
    
	/**
	 * 发表日记
	 * @param title 日记标题 
	 * @param content 日记内容 
	 * @param privacy 隐私设置 0：任何人可见 1：好友可见 2：凭密码 3：隐藏 默认为0
	 * @param atfriends 提到的好友UID 多个用英文半角逗号分隔
	 * @param allow_repaste 是否允许转贴 1：允许 0：不允许 仅在privacy为1时有效，默认为允许转帖。其他情况下该参数无效
	 * @param password 密码 仅在权限设置为“凭密码访问”时有效
	 * @return 日记ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope create_diary 
	 */
	public Response createDiary(String title, String content, int privacy, String atfriends, int allow_repaste, String password) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("title", title));
		params.add(new PostParameter("content", content));
		params.add(new PostParameter("privacy", privacy));
		params.add(new PostParameter("atfriends", atfriends));
		params.add(new PostParameter("allow_repaste", allow_repaste));
		params.add(new PostParameter("password", password));
	    return post("diary/create", params);
	}
	/**
	 * 我的日记
	 * @param start 起始值 
	 * @param num 返回日记的个数，默认为10，一次最多能获取10条 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_diary 
	 */
	public Response getDiary(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "diary/me", params);
		}
	/**
	 * 获取好友日记
	 * @param start 起始值 
	 * @param num 返回日记的个数，默认为10，一次最多能获取10条 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope friends_diary 
	 */
	public Response getMFDiary(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "diary/friends", params);
		}
	/**
	 * 提到我的日记
	 * @param start 起始值 
	 * @param num 返回日记的个数，默认为10，一次最多能获取10条 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_diary  
	 */
	public Response getDiaryAtme(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "diary/tag", params);
		}
	/**
	 * 用户的日记
	 * @param uid 用户ID 
	 * @param start 起始值 
	 * @param num 返回日记的个数，默认为10，一次最多能获取10条 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope friends_diary  
	 */
	public Response getDiaryByUid(long uid, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "diary/user", params);
		}
	/**
	 * 日记详细
	 * @param uid 用户ID 
	 * @param did 日记ID 
	 * @param password 密码 仅在权限设置为“凭密码访问”时有效
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_diary friends_diary 。如果只设置 scope=user_diary，只能获取到当前用户的日记详细内容；如果只设置 scope=friends_diary，则只能获取到好友的日记详细内容。
	 */
	public Response getDiaryDetail(long uid, long did, String password) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("did", did));
		params.add(new PostParameter("password", password));
	    return post( "diary/show", params);
	}
}