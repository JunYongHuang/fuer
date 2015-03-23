package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Sysnews extends KxAPIcaller {

    public Sysnews(String token)
    {
    	super(token);
    }
    
	/**
	 * 发表系统消息
	 * @param fuids 系统消息接收用户的uid,多个用户用半角逗号隔开，最多30个 
	 * @param linktext 动态里面的链接文字，不超过15个汉字。例如，链接文案：去xx帮忙。 
	 * @param link 动态里的链接地址。必须以http或https开头。 
	 * @param text 动态里用户的附言 
	 * @param word 发送动态所使用的文案，不超过60个汉字，否则会被截断。该文案可以有{_USER_} {_USER_TA_}变量，解析时会被替换为当前用户名字和他/她。例如，动态文案：{_USER_} 在做XX任务时遇到了强大的XX，快去帮帮{_USER_TA_}！ 
	 * @param picurl 发送动态所使用的图片地址，如果动态分享中需要发布图片，则此项必填。单张图片时，大小为80×80。 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope send_sysnews
	 */
	public Response sendSysnews(String fuids, String linktext, String link, String text, String word, String picurl) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("fuids", fuids));
		params.add(new PostParameter("linktext", linktext));
		params.add(new PostParameter("link", link));
		params.add(new PostParameter("text", text));
		params.add(new PostParameter("word", word));
		params.add(new PostParameter("picurl", picurl));
	    return post("sysnews/send", params);
	}
	/**
	 * 发表邀请
	 * @param fuids 邀请接收用户uid，多个UID用“，”隔开 
	 * @param word 动态里用户的附言 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope send_invitation
	 */
	public Response sendInvitation(String fuids, String word) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("fuids", fuids));
		params.add(new PostParameter("word", word));
	    return post("sysnews/invitation", params);
	}
}