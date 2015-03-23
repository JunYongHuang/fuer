package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Gift extends KxAPIcaller {

    public Gift(String token)
    {
    	super(token);
    }
    
	/**
	 * 获取普通礼物的列表 
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @scope basic
	 */
	public Response getGifts(int start, int num) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "gift/list", params);
		}
	/**
	 * 赠送礼物给好友
	 * @param gid 礼物id 
	 * @param reveuids 接收礼物的好友uid，多个uid用半角逗号隔开 
	 * @param words 赠送附言 
	 * @param anonymous 是否匿名赠送（0不是，1是，默认为0） 
	 * @param invisible 是否悄悄赠送（0不是，1是，默认为0） 
	 * @return 返回结果 1成功 0不成功
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response sendGift(long gid, String reveuids, String words, int anonymous, int invisible) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("gid", gid));
		params.add(new PostParameter("reveuids", reveuids));
		params.add(new PostParameter("words", words));
		params.add(new PostParameter("anonymous", anonymous));
		params.add(new PostParameter("invisible", invisible));
	    return post("gift/send", params);
	}
	/**
	 * 获取当前用户收到的礼物列表 
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @scope basic
	 */
	public Response getRevGifts(int start, int num) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "gift/received", params);
		}
	/**
	 * 当前用户送出的礼物列表 
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @scope basic
	 */
	public Response getSendGifts(int start, int num) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "gift/delivered", params);
		}
}