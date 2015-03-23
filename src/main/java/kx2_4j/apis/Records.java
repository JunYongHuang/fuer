
package kx2_4j.apis;

import java.io.File;
import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Records extends KxAPIcaller {

    public Records(String token)
    {
    	super(token);
    }
    
	/**
	 * 获取我的记录列表
	 * @param start 展示起始条数，默认为0
	 * @param num 展示条数，默认为20
	 * @param category 分类条件，0/1/2/3/4/5/6/7：全部/原创/转发/签名/公开/仅好友可见/仅自己可见/好友的好友可见
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_records
	 */
	public Response  getMyRecords(int start, int num, int category) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("category", category));
	    return get( "records/me", params);
	}
	/**
	 * 获取所有好友的新记录
	 * @param start 展示起始条数，默认为0
	 * @param num 展示条数，默认为20
	 * @param category 分类条件0：全部,1：原创,2：转发,3：签名
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope friends_records
	 */
	public Response getFriendsRecords(int start, int num, int category) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("category", category));
	    return get( "records/friends", params);
	}
	/**
	 * 获取“随便看看”记录列表
	 * @param start 展示起始条数，默认为0
	* @param num 展示条数，默认为20
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getPublicRecords(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "records/public", params);
	}
	/**
	 * 获取某一用户的记录列表
	 * @param start 展示起始条数，默认为0
	 * @param num 展示条数，默认为20
	 * @param uid 用户id，默认为调用接口用户
	 * @param category 分类条件,0：全部,1：原创,2：转发,3：签名
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope friends_records
	 */
	public Response getRecordsByUid(int start, int num, long uid, int category) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("category", category));
	    return get( "records/user", params);
	}
	/**
	 * 搜索记录，搜索全站隐私设置为“任何人可见”的记录
	 * @param start 展示起始条数，默认为0
	 * @param num 展示条数，默认为20
	 * @param keyword 搜索关键词
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response searchRecords(int start, int num, String keyword) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("keyword", keyword));
	    return get( "records/search", params);
	}
	/**
	 * 获取记录的热门话题，返回数量不定
	 * @param  
	 * @return
	 * @throws KxException
	 * @scope basic
	 */
	public Response getTopicsRecords( ) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    return get( "records/topics", params);
	}
	/**
	 * 删除记录
	 * @param rid 记录ID
	 * @return 成功返回1
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response deleteRecord(String rid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("rid", rid));
	    return post("records/delete", params);
	}

	/**
	 * 发表一条记录(可带图)
	 * @param content 记录内容 
	 * @param save_to_album 存到记录相册中 0：不保存 1：保存 默认为0
	 * @param location 地理位置 
	 * @param lat 纬度 -90.0到+90.0，+表示北纬
	 * @param lon 经度 -180.0到+180.0，+表示东经
	 * @param sync_status 同步到签名 0：无任何操作 1：同步 2：不同步 默认为0
	 * @param spri 权限设置 0：任何人可见 1：好友可见 2：仅自己可见 3：好友的好友可见 默认为0
	 * @param pic 图片 发记录上传的图片，图片在10M以内，格式支持jpg/jpeg/gif/png/bmp
	 * @param picurl 图片链接 图片在10M以内，格式支持jpg/jpeg/gif/png/bmp。pic和picurl同时提交时，只取pic
	 * @return 记录ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope create_records 
	 */
	public Response addRecord(String content, int save_to_album, String location, float lat, float lon, int sync_status, int spri, String pic, String picurl) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("content", content));
		params.add(new PostParameter("save_to_album", save_to_album));
		params.add(new PostParameter("location", location));
		params.add(new PostParameter("lat", lat));
		params.add(new PostParameter("lon", lon));
		params.add(new PostParameter("sync_status", sync_status));
		params.add(new PostParameter("spri", spri));
		params.add(new PostParameter("picurl", picurl));
	    if(pic != null && pic.length()>0)
		{
			File file = new File(pic);
			if(!file.exists())
			{
				System.out.println("打不开文件: "+pic);
				return null;
			}
			params.add(new PostParameter("pic",file));
			return multPartURL("records/add", params);
		}
		else
		{
			return post("records/add", params);
		}
	}
}