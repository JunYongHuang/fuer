package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Vote extends KxAPIcaller {

    public Vote(String token)
    {
    	super(token);
    }
    
	/**
	 * 回复投票
	 * @param vid 投票ID 
	 * @param answers 投票项，多个选项用“,”分隔 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response vote(long vid, String answers) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("vid", vid));
		params.add(new PostParameter("answers", answers));
	    return post("vote/reply", params);
	}
	/**
	 * 发起投票
	 * @param maxnum 最大允许投票数（> 1 and <= 20） 
	 * @param gender 性别限制0/1/2 无限制/限制女性/限制男性 
	 * @param enddate 截止时间（格式是2011-11-12 10:11:12） 
	 * @param friendonly 是否只允许好友投票 
	 * @param topic 投票标题 
	 * @param intro 投票简介 
	 * @param answer1 选项 
	 * @param answer2 选项 
	 * @param answer3 选项 
	 * @param answer4 选项 
	 * @param answer5 选项 
	 * @param answer6 选项 
	 * @param answer7 选项 
	 * @param answer8 选项 
	 * @param answer9 选项 
	 * @param answer10 选项 
	 * @param answer11 选项 
	 * @param answer12 选项 
	 * @param answer13 选项 
	 * @param answer14 选项 
	 * @param answer15 选项 
	 * @param answer16 选项 
	 * @param answer17 选项 
	 * @param answer18 选项 
	 * @param answer19 选项 
	 * @param answer20 选项 
	 * @return 投票ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response createVote(int maxnum, int gender, String enddate, int friendonly, String topic, String intro, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6, String answer7, String answer8, String answer9, String answer10, String answer11, String answer12, String answer13, String answer14, String answer15, String answer16, String answer17, String answer18, String answer19, String answer20) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("maxnum", maxnum));
		params.add(new PostParameter("gender", gender));
		params.add(new PostParameter("enddate", enddate));
		params.add(new PostParameter("friendonly", friendonly));
		params.add(new PostParameter("topic", topic));
		params.add(new PostParameter("intro", intro));
		params.add(new PostParameter("answer1", answer1));
		params.add(new PostParameter("answer2", answer2));
		params.add(new PostParameter("answer3", answer3));
		params.add(new PostParameter("answer4", answer4));
		params.add(new PostParameter("answer5", answer5));
		params.add(new PostParameter("answer6", answer6));
		params.add(new PostParameter("answer7", answer7));
		params.add(new PostParameter("answer8", answer8));
		params.add(new PostParameter("answer9", answer9));
		params.add(new PostParameter("answer10", answer10));
		params.add(new PostParameter("answer11", answer11));
		params.add(new PostParameter("answer12", answer12));
		params.add(new PostParameter("answer13", answer13));
		params.add(new PostParameter("answer14", answer14));
		params.add(new PostParameter("answer15", answer15));
		params.add(new PostParameter("answer16", answer16));
		params.add(new PostParameter("answer17", answer17));
		params.add(new PostParameter("answer18", answer18));
		params.add(new PostParameter("answer19", answer19));
		params.add(new PostParameter("answer20", answer20));
	    return post("vote/post", params);
	}
	/**
	 * 获取所有好友的投票 
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getMFVotes(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "vote/feed", params);
		}
	/**
	 * 获取好友的投票 
	 * @param uid 起始值 
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getVotesByUid(long uid, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "vote/friend", params);
		}
	/**
	 * 获取我发起的投票情况
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getVotesByMe(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "vote/mypost", params);
	}
	/**
	 * 获取我的所有投票
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getMyVotes(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "vote/me", params);
		}
	/**
	 * 获取投票详情
	 * @param vid 投票ID 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getVoteDetail(long vid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("vid", vid));
	    return get( "vote/detail", params);
	}
	/**
	 * 获取最新投票 
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getNewVotes(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "vote/newlist", params);
		}
	/**
	 * 获取热门投票 
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getHotVotes(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "vote/hot", params);
		}
}