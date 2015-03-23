
package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Album extends KxAPIcaller {

    public Album(String token)
    {
    	super(token);
    }
    
	/**
	 * 创建照片专辑
	 * @param title 照片专辑标题
	 * @param privacy 照片专辑隐私设置(0:任何人可见,
	 * @param password 照片专辑密码(privacy为2时必填)
	 * @param category 照片专辑分类(0:空,1:美女,2:帅哥,3:宠物,4:旅游,5:美食,6:家居,7:街拍,8:时尚,9:风景,10:奇趣)
	 * @param allow_repaste 是否允许转贴，仅在privacy为1时有效，默认为允许转帖。其他情况下该参数无效
	 * @param location 照片专辑拍摄地点
	 * @param description 照片专辑描述
	 * @return 创建成功的照片专辑ID
	 * @throws KxException
	 * @throws JSONException 
	 * @scope create_album 
	 */
	public Response createAlbum(String title, int privacy, String password, int category, int allow_repaste, String location, String description) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("title", title));
		params.add(new PostParameter("privacy", privacy));
		params.add(new PostParameter("password", password));
		params.add(new PostParameter("category", category));
		params.add(new PostParameter("allow_repaste", allow_repaste));
		params.add(new PostParameter("location", location));
		params.add(new PostParameter("description", description));
	    return post("album/create", params);
	}
	/**
	 * 返回某个用户的照片专辑列表。若参数uid缺失, 默认使用当前登录用户的UID。
	 * @param uid 用户UID。
	 * @param start 起始值。
	 * @param num 返回数量。
	 * @return 
	 * @throws KxException
	 * @scope user_photo friends_photo 
	 * 如果只设置 scope=user_photo，只能获取到当前用户的照片专辑列表；如果只设置 scope=friends_photo，则只能获取到好友的照片专辑列表。
	 */
	public Response getAlbumsByUid(long uid, int start, int num) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("uid", uid));
		params.add(new PostParameter("start", start));
		params.add(new PostParameter("num", num));
	    return get( "album/show", params);
	}
}