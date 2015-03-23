package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Forward extends KxAPIcaller {

    public Forward(String token)
    {
    	super(token);
    }
    
	/**
	 * 对某一资源进行转发
	 * @param objtype 被转发资源的类型，目前支持的资源有：photo, album, records, diary 
	 * @param objid 被转发资源的ID 
	 * @param ouid 被转发资源所有者的UID 
	 * @param content 转发内容 
	 * @return 转发ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response createForward(String objtype, long objid, long ouid, String content) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
		params.add(new PostParameter("content", content));
	    return post("forward/create", params);
	}
	/**
	 * 对某一资源进行再次转发
	 * @param objtype 被转发资源的类型，目前支持的资源有：photo, album, records, diary 
	 * @param objid 被转发资源的ID 
	 * @param ouid 被转发资源所有者的UID 
	 * @param fid 转发id 
	 * @param content 转发内容 
	 * @return 转发ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response reForward(String objtype, long objid, long ouid, long fid, String content) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
		params.add(new PostParameter("fid", fid));
		params.add(new PostParameter("content", content));
	    return post("forward/reforward", params);
	}
	/**
	 * 获取某一资源的所有转发
	 * @param objtype 被转发资源的类型，目前支持的资源有：photo, album, records, diary 
	 * @param objid 被转发资源的ID 
	 * @param ouid 被转发资源所有者的UID 
	 * @param start 起始 
	 * @param num 返回数量，默认10 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getForwardDetail(String objtype, long objid, long ouid, int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("objtype", objtype));
		params.add(new PostParameter("objid", objid));
		params.add(new PostParameter("ouid", ouid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "forward/list", params);
	}
	/**
	 * 获取对当前用户内容的所有转发
	 * @param start 起始 
	 * @param num 返回数量，默认10 
	 * @return 
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getForwards(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
		return get( "forward/me", params);
	}
}