package kx2_4j.apis;

import java.io.File;
import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Lbs extends KxAPIcaller {

    public Lbs(String token)
    {
    	super(token);
    }
    
	/**
	 * 签到
	 * @param lat 纬度 
	 * @param lon 经度 
	 * @param loc_name 地点名称 
	 * @param addr 地址 
	 * @param content 签到内容 
	 * @param coop_link 来源url 
	 * @param pic 图片文件 
	 * @param privacy 隐私设置（默认为0）0：所有人可见 1：好友可见 2：仅自己可见 
	 * @param country 国家（如中国） 
	 * @param province 省份（如河北省） 
	 * @param city 城市（如合肥市） 
	 * @return 签到id
	 * @throws KxException
	 * @throws JSONException 
	 * @scope places_checkin 
	 */
	public Response checkin(float lat, float lon, String loc_name, String addr, String content, String coop_link, String pic, int privacy, String country, String province, String city) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("lat", lat));
		params.add(new PostParameter("lon", lon));
		params.add(new PostParameter("loc_name", loc_name));
		params.add(new PostParameter("addr", addr));
		params.add(new PostParameter("content", content));
		params.add(new PostParameter("coop_link", coop_link));
		params.add(new PostParameter("privacy", privacy));
		params.add(new PostParameter("country", country));
		params.add(new PostParameter("province", province));
		params.add(new PostParameter("city", city));
		if(pic!=null && pic.length()>0)
		{
			File file = new File(pic);
	        if(!file.exists())
	    	{
	    		System.out.println("打不开文件: "+pic);
	    		return null;
	    	}
			params.add(new PostParameter("pic",file));
	        return multPartURL("lbs/checkin", params);
		}
        return post("lbs/checkin", params);
	}
	/**
	 * 当前用户的签到列表
	 * @param start 起始值 
	 * @param num 返回数量(可选，默认为20) 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_places 
	 */
	public Response getLBS(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "lbs/me", params);
	}
	/**
	 * 好友的签到列表
	 * @param lon 经度 
	 * @param lat 纬度 
	 * @param start 起始值 
	 * @param num 返回数量(默认为20) 
	 * @return 
	 * @throws KxException
	 * @scope friends_places 
	 */
	public Response getMFLBS(float lon, float lat, int start, int num) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("lon", lon));
		params.add(new PostParameter("lat", lat));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "lbs/friend", params);
		}
	/**
	 * 附近的朋友
	 * @param lon 经度 
	 * @param lat 纬度 
	 * @param start 起始值 
	 * @param num 返回数量(默认为20) 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope friends_places 
	 */
	public Response getNearfriends(float lon, float lat, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("lon", lon));
		params.add(new PostParameter("lat", lat));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "lbs/near_friends", params);
		}
	/**
	 * 附近的陌生人
	 * @param lon 经度 
	 * @param lat 纬度 
	 * @param start 起始值 
	 * @param num 返回数量(默认为20) 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic 
	 */
	public Response getNearusers(float lon, float lat, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("lon", lon));
		params.add(new PostParameter("lat", lat));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "lbs/near_users", params);
		}
	/**
	 * 获取附近照片
	 * @param lon 经度 
	 * @param lat 纬度 
	 * @param start 起始值 
	 * @param num 返回数量(默认为20) 
	 * @return 
	 * @throws KxException
	 * @scope basic 
	 */
	public Response getNearPhotos(float lon, float lat, int start, int num) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("lon", lon));
		params.add(new PostParameter("lat", lat));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "lbs/photo", params);
		}
	/**
	 * 创建一个poi
	 * @param loc_name 名字 
	 * @param addr 地址 
	 * @param lon 经度 
	 * @param lat 纬度 
	 * @return 新增地点ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope places_checkin 
	 */
	public Response createPlace(String loc_name, String addr, float lon, float lat) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("loc_name", loc_name));
		params.add(new PostParameter("addr", addr));
		params.add(new PostParameter("lon", lon));
		params.add(new PostParameter("lat", lat));
	    return get( "lbs/create", params);
	}
}