package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Friends extends KxAPIcaller {

    public Friends(String token)
    {
        super(token);
    }

    /**
     * 根据UID获取多个用户的资料
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
    public Response getFriends(String fields, int start, int num) throws KxException, JSONException
    {
        ArrayList<PostParameter> params = new ArrayList<PostParameter>();
        if (fields != null)
        {
            params.add(new PostParameter("fields", fields));
        }
        params.add(new PostParameter("start", start));
        params.add(new PostParameter("num", num));
        return get( "friends/me", params);
    }

    /**
     * wrapper for getFriends(String uids, String fields, int start, int num)
     * @param fields
     * @return
     * @throws KxException
     * @throws JSONException
     */
    public Response getFriends(String fields) throws KxException, JSONException
    {
        return getFriends(fields,0,20);
    }

    /**
     * 获取两个用户间的好友关系
     * @param uid1 用户ID
     * @param uid2 用户ID
     * @return 用户uid1相对用户uid2的好友关系    0: 没有关系,1: 偶像,2: 粉丝,3: 互为好友
     * @throws KxException
     * @throws JSONException
     */
    public Response isFriend(long uid1,long uid2) throws KxException,JSONException
    {
        ArrayList<PostParameter> params = new ArrayList<PostParameter>();
        params.add(new PostParameter("uid1", uid1));
        params.add(new PostParameter("uid2", uid2));
        return get( "friends/relationship", params);
    }

    /**
     * 获取两个用户的共同好友列表
     * @param uid1
     * @param uid2
     * @return
     * @throws KxException
     * @throws JSONException
     * @scope basic
     */
    public Response getMutualFri(long uid1, long uid2) throws KxException, JSONException
    {
        ArrayList<PostParameter> params = new ArrayList<PostParameter>();
        params.add(new PostParameter("uid1", uid1));
        params.add(new PostParameter("uid2", uid2));
        return get( "friends/mutual", params);
    }
    /**
     * 添加好友
     * @param touid 要添加为好友的用户ID
     * @param code 用户输入的验证码
     * @param rcode 验证码识别串
     * @param content 好友请求说明
     * @return 加好友返回的状态 成功为1 失败返回相应信息
     * @throws KxException
     * @throws JSONException
     * @scope basic
     */
    public Response addFriend(long touid, String code, String rcode, String content) throws KxException, JSONException
    {
        ArrayList<PostParameter> params = new ArrayList<PostParameter>();
        params.add(new PostParameter("touid", touid));
        params.add(new PostParameter("code", code));
        params.add(new PostParameter("rcode", rcode));
        params.add(new PostParameter("content", content));
        return post("friends/add", params);
    }
}