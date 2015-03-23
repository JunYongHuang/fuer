package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Like extends KxAPIcaller {

    public Like(String token)
    {
    	super(token);
    }
    
	/**
	 * 表达对某一资源的赞
	 * @param objtype 被赞资源的类型 目前支持的资源有：photo, album, records, diary, repaste
	 * @param objid 被赞资源的ID 
	 * @param ouid 被赞资源所有者的UID 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response createLike(String objtype, long objid, long ouid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
	    return get( "like/create", params);
	}
	/**
	 * 取消对某一资源的赞
	 * @param objtype 被赞资源的类型 目前支持的资源有：photo, album, records, diary, repaste
	 * @param objid 被赞资源的ID 
	 * @param ouid 被赞资源所有者的UID 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response cancelLike(String objtype, long objid, long ouid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
	    return get( "like/cancel", params);
	}
	/**
	 * 判断用户是否对某一资源赞过
	 * @param objtype 被赞资源的类型 目前支持的资源有：photo, album, records, diary, repaste
	 * @param objid 被赞资源的ID 
	 * @param ouid 被赞资源所有者的UID 
	 * @param uids 待判断的用户ID 多个UID用半角逗号隔开
	 * @return 
	 * @throws KxException
	 * @scope basic
	 */
	public Response checkLike(String objtype, long objid, long ouid, String uids) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
		params.add(new PostParameter("uids", uids));
		return get( "like/check", params);
		}
	/**
	 * 获取对某一资源赞过的用户列表
	 * @param objtype 被赞资源的类型 目前支持的资源有：photo, album, records, diary, repaste
	 * @param objid 被赞资源的ID 
	 * @param ouid 被赞资源所有者的UID 
	 * @param start 起始位置，默认是0 
	 * @param num 返回数量，默认是10 
	 * @return 
	 * @throws KxException
	 * @scope basic
	 */
	public Response getLikedUsers(String objtype, long objid, long ouid, int start, int num) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "like/show", params);
		}
}