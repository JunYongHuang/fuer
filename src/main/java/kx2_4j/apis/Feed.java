package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Feed extends KxAPIcaller {

    public Feed(String token)
    {
    	super(token);
    }
    
	/**
	 * 发表动态
	 * @param linktext 动态里面的链接文字，不超过15个汉字。例如，链接文案：去xx帮忙。 
	 * @param link 动态里的链接地址。必须以http或https开头。 
	 * @param text 动态里用户的附言，不能超过50个字 
	 * @param word 发送动态所使用的文案，不超过60个汉字，否则会被截断。该文案可以有{_USER_} {_USER_TA_}变量，解析时会被替换为当前用户名字和他/她。例如，动态文案：{_USER_} 在做XX任务时遇到了强大的XX，快去帮帮{_USER_TA_}! 
	 * @param picurl 发送动态所使用的图片地址，如果动态分享中需要发布图片，则此项必填。单张图片时，大小为80×80。 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope send_feed 
	 */
	public Response sendFeed(String linktext, String link, String text, String word, String picurl) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("linktext", linktext));
		params.add(new PostParameter("link", link));
		params.add(new PostParameter("text", text));
		params.add(new PostParameter("word", word));
		params.add(new PostParameter("picurl", picurl));
	    return post("feed/send", params);
	}

	/**
	 * 获取用户的动态，动态内容包含照片(photo)、记录(records)、日记(diary)、转帖(repaste)。如果指定好友uid，则获取该好友的动态内容。
	 * @param num 返回动态的条数 
	 * @param time 更新时间（UNIX时间戳），获取此更新时间之前的动态，默认返回全部动态 
	 * @param fuid 用户id，可以是当前用户或其好友的id，为空时返回当前用户的动态 
	 * @param objtype 动态内容的类型，可以是photo, diary, records, repaste，分别为照片、日记、记录、转帖；默认返回全部动态 
	 * @return 
	 * @throws KxException
	 * @scope user_feed friends_feed
	 */
	public Response getFeedsByUid(int num, long time, long fuid, String objtype) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    if(num>0)params.add(new PostParameter("num", num));
	    if(time>0)params.add(new PostParameter("time", time));
	    if(fuid>0)params.add(new PostParameter("fuid", fuid));
		if(objtype.length()>0)params.add(new PostParameter("objtype", objtype));
	    return get( "feed/user", params);
		}
}