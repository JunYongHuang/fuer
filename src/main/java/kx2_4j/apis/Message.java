
package kx2_4j.apis;

import java.io.File;
import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Message extends KxAPIcaller {

    public Message(String token)
    {
    	super(token);
    }
    
	/**
	 * 收件箱
	 * @param start 短消息列表起始，默认为0
	 * @param num 短消息列表展示数，默认为30
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_messagebox 
	 */
	public Response getReceivedMessage(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "message/inbox", params);
	}
	/**
	 * 发件箱
	 * @param start 短消息列表起始，默认为0
	 * @param num 短消息列表展示数，默认为30
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_messagebox 
	 */
	public Response getSendedMessage(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "message/outbox", params);
	}
	/**
	 * 短消息的详细信息
	 * @param mid 短消息id
	 * @param start 短消息列表起始，默认为0
	 * @param num 短消息列表展示数，默认为30
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_messagebox
	 */
	public Response getDetailofMessage(long mid, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("mid", mid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "message/detail", params);
	}

	/**
	 * 发送短消息 
	 * @param content 消息内容 
	 * @param fuids 收件人UID 多个UID用半角逗号隔开
	 * @param attachment 附件 
	 * @param attachment1 附件1 
	 * @param attachment2 附件2 
	 * @param attachment3 附件3 
	 * @param attachment4 附件4 
	 * @return 短消息ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope send_message 
	 */
	public Response sendMessage(String content, String fuids, String attachment, String attachment1, String attachment2, String attachment3, String attachment4) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("content", content));
		params.add(new PostParameter("fuids", fuids));
	    if(attachment != null)
		{
	    	this.constructAttachMap(params,attachment, attachment1, attachment2, attachment3, attachment4);
			return multPartURL("message/send", params);
		}
		else
		{
			return post("message/send", params);
		}
	}
	/**
	 * 回复短消息
	 * @param content 消息内容 
	 * @param mid 短消息ID 
	 * @param attachment 附件 
	 * @param attachment1 附件1 
	 * @param attachment2 附件2 
	 * @param attachment3 附件3 
	 * @param attachment4 附件4 
	 * @return 短消息ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope send_message 
	 */
	public Response replyMessage(String content, long mid, String attachment, String attachment1, String attachment2, String attachment3, String attachment4) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("content", content));
		params.add(new PostParameter("mid", mid));
		if(attachment != null)
		{
	    	this.constructAttachMap(params, attachment, attachment1, attachment2, attachment3, attachment4);
			return multPartURL("message/reply", params);
		}
		else
		{
			return post("message/reply", params);
		}
	}
	private void constructAttachMap(ArrayList<PostParameter> params,String attachment, String attachment1, String attachment2, String attachment3, String attachment4)
	{
		File file = new File(attachment);
		if(!file.exists())
		{
			System.out.println("打不开文件: "+attachment);
			return;
		}
		params.add(new PostParameter("attachment", file));
		if(attachment1 != null)
		{
			File file1 = new File(attachment1);
			if(!file1.exists())
			{
				System.out.println("打不开文件: "+attachment1);
				return;
			}
			params.add(new PostParameter("attachment1", file1));
		}
		if(attachment2 != null)
		{
			File file2 = new File(attachment2);
			if(!file2.exists())
			{
				System.out.println("打不开文件: "+attachment2);
				return;
			}
			params.add(new PostParameter("attachment2", file2));
		}
		if(attachment3 != null)
		{
			File file3 = new File(attachment3);
			if(!file3.exists())
			{
				System.out.println("打不开文件: "+attachment3);
				return;
			}
			params.add(new PostParameter("attachment3", file3));
		}
		if(attachment4 != null)
		{
			File file4 = new File(attachment4);
			if(!file4.exists())
			{
				System.out.println("打不开文件: "+attachment4);
				return;
			}
			params.add(new PostParameter("attachment4", file4));
		}
	}
}