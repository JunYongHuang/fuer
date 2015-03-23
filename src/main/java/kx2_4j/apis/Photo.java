
package kx2_4j.apis;

import java.io.File;
import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Photo extends KxAPIcaller {

    public Photo(String token)
    {
    	super(token);
    }
    
	/**
	 * 获取某个用户的某张照片或某照片专辑下的所有照片
	 * @param uid 用户UID。若参数uid缺失, 默认使用当前登录用户的UID。
	 * @param albumid 照片专辑ID。 若参数albumid和pid都缺失, 返回错误提示Photo_Parameter_Absent。
	 * @param pid 照片ID。 若参数albumid存在并且pid不存在, 则返回该相册下的照片列表。
	 * @param password 相册或照片的密码
	 * @param start 起始值。
	 * @param num 返回数量。
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_photo friends_photo 
	 * 如果只设置 scope=user_photo，只能获取到当前用户的照片；如果只设置 scope=friends_photo，则只能获取到好友的照片。
	 */
	public Response getPhotos(long uid, long albumid, long pid, String password, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("albumid", albumid));
		params.add(new PostParameter("pid", pid));
		params.add(new PostParameter("password", password));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return post( "photo/show", params);
	}
	/**
	 * 获取所有好友最新上传的照片列表
	 * @param start 起始值。（选填，默认值从0开始）
	 * @param num 返回数量。（选填， 默认20个）
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getLatestPhotos(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "photo/latest", params);
	}

	/**
	 * 上传一张照片到指定照片专辑
	 * @param albumid 专辑ID 
	 * @param title 标题 
	 * @param size 返回的照片大小  可选值：mid、small、cover
	 * @param send_news 是否发送动态 0：不发送动态, 1：发送动态
	 * @param pic 照片文件 
	 * @param tag tag标签 
	 * @return 照片id等信息
	 * @throws KxException
	 * @throws JSONException 
	 * @scope upload_photo  
	 */
	public Response uploadPic(long albumid, String title, String size, int send_news, String pic, String tag) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("albumid", albumid));
		params.add(new PostParameter("title", title));
		params.add(new PostParameter("size", size));
		params.add(new PostParameter("send_news", send_news));
		params.add(new PostParameter("tag", tag));
	    File file = new File(pic);
		if(!file.exists())
		{
			System.out.println("打不开文件: "+pic);
			return null;
		}
		params.add(new PostParameter("pic",file));
		return multPartURL("photo/upload", params);
	}
}