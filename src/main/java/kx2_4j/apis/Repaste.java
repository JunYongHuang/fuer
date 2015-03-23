package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Repaste extends KxAPIcaller {

    public Repaste(String token)
    {
    	super(token);
    }
    
	/**
	 * 创建转帖
	 * @param title 转帖标题 
	 * @param rcontent 转帖内容 支持HTML标签
	 * @param vpmethod 互动观点的类型 1：标签方式(tag)　2：投票方式(vote)
	 * @param tag 标签观点文案 如果选择了标签方式，此项必填
	 * @param answer1 投票观点文案 如果选择了投票方式，answer1-answer8其中两项必填
	 * @param answer2 投票观点文案 
	 * @param answer3 投票观点文案 
	 * @param answer4 投票观点文案 
	 * @param answer5 投票观点文案 
	 * @param answer6 投票观点文案 
	 * @param answer7 投票观点文案 
	 * @param answer8 投票观点文案 
	 * @param source_url 原帖网址 如是视频转帖，请直接填写视频原页面地址
	 * @return 转帖是否成功创建 0:失败 1:成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope create_repaste
	 */
	public Response createRepaste(String title, String rcontent, int vpmethod, String tag, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6, String answer7, String answer8, String source_url) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("title", title));
		params.add(new PostParameter("rcontent", rcontent));
		params.add(new PostParameter("vpmethod", vpmethod));
		params.add(new PostParameter("tag", tag));
		params.add(new PostParameter("answer1", answer1));
		params.add(new PostParameter("answer2", answer2));
		params.add(new PostParameter("answer3", answer3));
		params.add(new PostParameter("answer4", answer4));
		params.add(new PostParameter("answer5", answer5));
		params.add(new PostParameter("answer6", answer6));
		params.add(new PostParameter("answer7", answer7));
		params.add(new PostParameter("answer8", answer8));
		params.add(new PostParameter("source_url", source_url));
	    return post("repaste/create", params);
	}
	/**
	 * 获取当前用户转帖列表
	 * @param start 起始值 
	 * @param num 返回转帖的个数，默认为10，一次最多能获取20条 
	 * @param type 转贴的类型 热门：hotrp 最新：default 视频：video 照片：photo 文章：text 经典：classic 默认为hotrp
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_repaste
	 */
	public Response getRepastes(int start, int num, String type) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("type", type));
		return get( "repaste/me", params);
		}
	/**
	 * 获取当前用户全部好友的转帖列表
	 * @param start 起始值 
	 * @param num 返回转帖的个数，默认为10，一次最多能获取20条 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope friends_repaste 
	 */
	public Response getMFRepastes(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "repaste/friends", params);
		}
	/**
	 * 获取热门转帖列表
	 * @param type 转贴的类型 热门：hotrp 最新：default 视频：video 照片：photo 文章：text 经典：classic 默认为hotrp
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getPublicRepastes(String type) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("type", type));
		return get( "repaste/public", params);
		}
	/**
	 * 获取当前用户某个好友的转帖列表
	 * @param uid  
	 * @param start 起始值 
	 * @param num 返回数量 默认为10，一次最多能获取10条
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope friends_repaste 
	 */
	public Response getRepastesByUid(long uid, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "repaste/user", params);
		}
	/**
	 * 获取转帖详细内容
	 * @param uid 当前查看的转贴UID 
	 * @param urpid 当前查看的转贴ID 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_repaste friends_repaste 如果只设置 scope=user_repaste，只能获取到当前用户的转帖详细内容；如果只设置 scope=friends_repaste，则只能获取到好友的转帖详细内容。
	 */
	public Response getRepasteDetail(long uid, long urpid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("urpid", urpid));
		return get( "repaste/show", params);
		}
	/**
	 * 添加转贴新TAG
	 * @param uid 当前查看的转贴UID 
	 * @param urpid 当前查看的转贴ID 
	 * @param tag TAG名称,标签长度不能超过16个汉字 
	 * @param share_repaste 是否转帖（默认share）share：转帖 unshare：不转帖 
	 * @return 添加转贴TAG观点的状态值 1:成功 0:失败
	 * @throws KxException
	 * @throws JSONException 
	 * @scope create_repaste 
	 */
	public Response addTag(long uid, long urpid, String tag, String share_repaste) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("urpid", urpid));
		params.add(new PostParameter("tag", tag));
		params.add(new PostParameter("share_repaste", share_repaste));
	    return post("repaste/add_tag", params);
	}
	/**
	 * 添加转贴互动观点
	 * @param uid 当前查看的转贴UID 
	 * @param urpid 当前查看的转贴ID 
	 * @param tagid TAG ID (二选一) 
	 * @param voteid 投票答案ID (二选一) 
	 * @param share_repaste 是否转帖（默认share）share：转帖 unshare：不转帖 
	 * @return 发表转贴观点的状态值 1:成功 0:失败
	 * @throws KxException
	 * @throws JSONException 
	 * @scope create_repaste 
	 */
	public Response addInteract(long uid, long urpid, long tagid, int voteid, String share_repaste) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("urpid", urpid));
		params.add(new PostParameter("tagid", tagid));
		params.add(new PostParameter("voteid", voteid));
		params.add(new PostParameter("share_repaste", share_repaste));
	    return post("repaste/interact", params);
	}
}