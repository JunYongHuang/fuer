package kx2_4j.apis;

import java.io.File;
import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Rgroup extends KxAPIcaller {

    public Rgroup(String token)
    {
    	super(token);
    }
    
	/**
	 * 创建圈子
	 * @param title 圈子名字 
	 * @param category 圈子类型 0：默认 1：同学 2：家人 3：同事 4：饭友 5：玩友 6：闺蜜 7：其他
	 * @param uids 要邀请的人UID 只能邀请好友，多个UID用半角逗号隔开
	 * @return 圈子ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope create_rgroup
	 */
	public Response createGroup(String title, int category, String uids) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("title", title));
		params.add(new PostParameter("category", category));
		params.add(new PostParameter("uids", uids));
	    return post("rgroup/group_create", params);
	}
	/**
	 * 圈子列表
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_rgroup
	 */
	public Response getGroups(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "rgroup/groups", params);
		}
	/**
	 * 圈子动态
	 * @param start 起始值 
	 * @param num 返回数量 
	 * @param gid 圈子ID 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_rgroup
	 */
	public Response getGroupNews(int start, int num, long gid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("gid", gid));
		return get( "rgroup/talks", params);
		}
	/**
	 * 发布圈子动态
	 * @param message 动态内容 
	 * @param gid 圈子ID 
	 * @param pic 图片 
	 * @return 动态ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope create_talk
	 */
	public Response creatTalk(String message, long gid, String pic) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("message", message));
		params.add(new PostParameter("gid", gid));
		params.add(new PostParameter("pic", pic));
	    File file = new File(pic);
        if(!file.exists())
        {
        	System.out.println("打不开文件: "+pic);
        	return null;
        }
		params.add(new PostParameter("pic",file));
		return multPartURL("rgroup/talk_create", params);
	}
	/**
	 * 圈子动态详情
	 * @param start 展示起始条数，默认为0 
	 * @param num 展示条数，默认为20 
	 * @param gid 圈子ID 
	 * @param tid 动态ID 
	 * @param order 回复的排列顺序 asc：时间正序排列 desc：时间反序排列 默认desc
	 * @return 
	 * @throws KxException
	 * @scope user_rgroup
	 */
	public Response getTalk_detail(int start, int num, long gid, long tid, String order) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("gid", gid));
		params.add(new PostParameter("tid", tid));
		params.add(new PostParameter("order", order));
	    return get( "rgroup/talk_detail", params);
	}
	/**
	 * 圈子成员列表
	 * @param start 展示起始条数，默认为0 
	 * @param num 展示条数，默认为20 
	 * @param gid 圈子ID 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_rgroup
	 */
	public Response getGroupMembers(int start, int num, long gid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("gid", gid));
		return get( "rgroup/members", params);
		}
	/**
	 * 圈子消息
	 * @param start 展示起始条数，默认为0 
	 * @param num 展示条数，默认为20 
	 * @param type 0/1 0：回复我的 1：我回复的
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_rgroup
	 */
	public Response getGroupNotices(int start, int num, int type) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("type", type));
		return get( "rgroup/notices", params);
		}
	/**
	 * 圈子照片列表
	 * @param start 展示起始条数，默认为0 
	 * @param num 展示条数，默认为20 
	 * @param gid 圈子ID 
	 * @param albumid 专辑ID 如果为空则展示整个圈子的照片
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_rgroup
	 */
	public Response getGroupPhotos(int start, int num, long gid, long albumid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		params.add(new PostParameter("gid", gid));
		params.add(new PostParameter("albumid", albumid));
		return get( "rgroup/photos", params);
		}
	/**
	 * 圈子照片
	 * @param gid 圈子ID 
	 * @param pid 照片ID 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope user_rgroup
	 */
	public Response getGroupPhotoDetail(long gid, long pid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("gid", gid));
		params.add(new PostParameter("pid", pid));
		return get( "rgroup/photo_detail", params);
		}
}