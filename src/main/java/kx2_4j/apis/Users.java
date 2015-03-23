package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Users extends KxAPIcaller {

    public Users(String token)
    {
    	super(token);
    }
    
	/**
     * 获取当前登录用户的资料
     * @param fields 允许用户自定义返回字段，多个属性之间用英文半角逗号作为分隔符。
     * 用户的所有属性：uid,name,gender,hometown,city,status,logo120,logo50,birthday,bodyform,blood,marriage,trainwith,interest,favbook,favmovie,favtv,idol,motto,wishlist,intro,education,schooltype,school,class,year,career,company,dept,beginyear,beginmonth,endyear,endmonth,isStar,pinyin,online
     * 用户的基本属性：uid,name,gender,logo50
     * 当fields为空的时候只返回用户的基本属性。
     * @return 
     * @throws KxException 
     * @scope 获取非基本属性，需要在授权前设置相应scope,全部scope是user_birthday user_bodyform user_blood user_marriage user_intro user_education user_career user_online
     * 详情请参考 http://wiki.open.kaixin001.com/index.php?id=获取当前登录用户的资料#返回结果字段说明
     */
	public Response getCurUser(String fields) throws KxException 
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    if (fields != null)
	    {
	    	params.add(new PostParameter("fields", fields));
	    }
	    return get( "users/me", params);
	}
	
	/**
	 * 根据UID获取多个用户的资料
	 * @param uids 用户ID，可以设置多个，以半角逗号分隔。如：123456,220993，最多不能超过50个。
	 * @param fields 允许用户自定义返回字段，多个属性之间用英文半角逗号作为分隔符。
     * 用户的所有属性：uid,name,gender,hometown,city,status,logo120,logo50,birthday,bodyform,blood,marriage,trainwith,interest,favbook,favmovie,favtv,idol,motto,wishlist,intro,education,schooltype,school,class,year,career,company,dept,beginyear,beginmonth,endyear,endmonth,isStar,pinyin,online
     * 用户的基本属性：uid,name,gender,logo50
     * 当fields为空的时候只返回用户的基本属性。
     * @param start
	 * @param num
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope 获取非基本属性，需要在授权前设置相应scope,全部scope是：
	 * user_birthday user_bodyform user_blood user_marriage user_intro user_education user_career user_online
	 * friends_birthday friends_bodyform friends_blood friends_marriage friends_intro friends_education friends_career friends_online
     * 详情请参考 http://wiki.open.kaixin001.com/index.php?id=获取当前登录用户的资料#返回结果字段说明
     */
	public Response getUsers(String uids, String fields, int start, int num) throws KxException, JSONException 
	{
		if (uids.length() == 0) {
            throw new KxException("uids参数为空");
        }
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
		params.add(new PostParameter("uids",uids));
	    if (fields != null)
	    {
	    	params.add(new PostParameter("fields", fields));
	    }
	    params.add(new PostParameter("start", start));
	    params.add(new PostParameter("num", num)); 
	    return get( "users/show", params);
	}
	
	/**
	 * wrapper for getUsers(String uids, String fields, int start, int num)
	 * @param uids
	 * @param fields
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 */
	public Response getUsers(String uids, String fields) throws KxException, JSONException
	{
		return getUsers(uids,fields,0,20);
	}
	
	/**
	 * 获取可能认识的人
	 * @param start 起始值，默认为0
	 * @param num 返回数量，默认为20，最多50
	 * @return
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response getMFriends(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
	    params.add(new PostParameter("num", num)); 
	    return get( "users/mfriends", params);
	}
	
	public Response getMFriends() throws KxException, JSONException
	{
	    return getMFriends(0,20);
	}
	
	public Response getAddedPage(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
	    params.add(new PostParameter("num", num)); 
	    return get( "users/page_list", params);
	}
	
	public Response getAddedPage() throws KxException, JSONException
	{
	    return getAddedPage(0,20);
	}

	public Response getVisitors(int start, int num) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("start", start));
	    params.add(new PostParameter("num", num)); 
	    return get( "users/visitors", params);
	}
	
	public Response getVisitors() throws KxException, JSONException
	{
	    return getVisitors(0,20);
	}
	
	public Response getNverifyinfo(long uid) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
	    return get( "users/nverifyinfo", params);
	}
	
	
}
