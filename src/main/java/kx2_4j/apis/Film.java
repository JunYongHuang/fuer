package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Film extends KxAPIcaller {

    public Film(String token)
    {
    	super(token);
    }
    
	/**
	 * 用户输入关键字（片名、演员或导演），返回相应的电影列表
	 * @param keyword 关键字 
	 * @param start 开始 
	 * @param num 每页显示条数 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response searchFilm(String keyword, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("keyword", keyword));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "film/search", params);
		}
	/**
	 * 对已看过的电影发表评论
	 * @param mid 电影id 
	 * @param score 给电影打分(范围0-5) 
	 * @param comment 影评 
	 * @param media 观看介质(0.其他1.电影 2.DVD 3.网上) 
	 * @param place 观看地点 
	 * @param friends 与谁一起看(填写uid，多个用逗号隔开，最多30个) 
	 * @param privacy 隐私设置(可选，1.所有人可见 2.仅好友可见 默认为1) 
	 * @param send_news 是否发布动态（1.发布 0.不发布，默认为1） 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response commentFilm(long mid, int score, String comment, int media, String place, String friends, int privacy, int send_news) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("mid", mid));
		params.add(new PostParameter("score", score));
		params.add(new PostParameter("comment", comment));
		params.add(new PostParameter("media", media));
		params.add(new PostParameter("place", place));
		params.add(new PostParameter("friends", friends));
		params.add(new PostParameter("privacy", privacy));
		params.add(new PostParameter("send_news", send_news));
	    return post("film/watched", params);
	}
	/**
	 * 将电影标记为想看
	 * @param mid 电影id 
	 * @param send_news 是否发布动态（1.发布 0.不发布，默认为1） 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response markWishFilm(long mid, int send_news) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("mid", mid));
		params.add(new PostParameter("send_news", send_news));
	    return post("film/wish", params);
	}
	/**
	 * 我想看的电影列表
	 * @param start 列表起始值，默认为0 
	 * @param num 获取数目，默认为20 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getMyWishFilms(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "film/wishlist", params);
		}
	/**
	 * 我推荐的电影列表
	 * @param start 列表起始值，默认为0 
	 * @param num 获取数目，默认为20 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getMyRecommendFilms(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "film/recommendlist", params);
		}
	/**
	 * 我看过的电影列表
	 * @param start 列表起始值，默认为0 
	 * @param num 获取数目，默认为20 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getWatchedFilms(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "film/sawlist", params);
		}
	/**
	 * 推荐一部电影
	 * @param mid 电影id 
	 * @param reason 推荐理由 
	 * @param uids 需要通知的好友（多个uid用“，”隔开） 
	 * @param firstpage 是否在首页个人资料栏里推荐此电影（0不 1是，默认0） 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response recommendFilm(long mid, String reason, String uids, int firstpage) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("mid", mid));
		params.add(new PostParameter("reason", reason));
		params.add(new PostParameter("uids", uids));
		params.add(new PostParameter("firstpage", firstpage));
	    return post("film/recommend", params);
	}
	/**
	 * 好友们看过和想看的电影
	 * @param start 列表起始值，默认为0 
	 * @param num 获取数目，默认为20 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getMFFilms(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "film/friendtouch", params);
		}
}