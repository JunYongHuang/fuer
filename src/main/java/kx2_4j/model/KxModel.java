package kx2_4j.model;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;

import kx2_4j.model.users.*;
import kx2_4j.model.gamefriends.*;
import kx2_4j.model.photo.*;
import kx2_4j.model.rgroup.*;
import kx2_4j.model.like.*;
import kx2_4j.model.comment.*;
import kx2_4j.model.records.*;
import kx2_4j.model.repaste.*;
import kx2_4j.model.message.*;
import kx2_4j.model.msgcenter.*;
import kx2_4j.model.diary.*;
import kx2_4j.model.emotion.*;
import kx2_4j.model.album.*;
import kx2_4j.model.app.*;
import kx2_4j.model.board.*;
import kx2_4j.model.feed.*;
import kx2_4j.model.forward.*;
import kx2_4j.model.lbs.*;
import kx2_4j.model.film.*;
import kx2_4j.model.vote.*;
import kx2_4j.model.gift.*;

import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;


public class KxModel {

	private static void getPaging(JSONObject json, Paging paging) throws JSONException
	{
		if(json == null)return;
		paging.prev = json.getInt("prev");
		paging.next = json.getInt("next");
	}
	
	private static void getPaging(JSONObject json, PagingWithTotal paging) throws JSONException
	{
		if(json == null)return;
		paging.total = json.getInt("total");
		paging.prev = json.getInt("prev");
		paging.next = json.getInt("next");
	}
	
	
	/*--------------------------------------------Users-------------------------------------*/
	public static User parseForUsersMe(Response response) throws KxException, JSONException
	{
		return new User(response);
	}
	
    public static User[] parseForUsersShow(Response response) throws KxException, JSONException
    {
    	return User.jsons2objs(response.asJSONObject().getJSONArray("users"));
    }
    
    public static UserBase[] parseForUsersMFreinds(Response response) throws KxException, JSONException
    {
    	return UserBase.jsons2objs(response.asJSONObject().getJSONArray("userlist"));
    }
    
    public static PageUser[] parseForUsersPage_list(Response response) throws KxException, JSONException
    {
    	return PageUser.jsons2objs(response.asJSONObject().getJSONArray("pagelist"));
    }
    
    public static UserBase[] parseForUsersVisitors(Response response) throws KxException, JSONException
    {
    	return UserBase.jsons2objs(response.asJSONObject().getJSONArray("visitorslist"));
    }

	public static Nverifyinfo parseForUsersNverifyinfo(Response response) throws KxException, JSONException
	{
		return new Nverifyinfo(response);
	}
	
	/*--------------------------------------------Friends-------------------------------------*/
	public static User[] parseForFriendsMe(Response response) throws KxException, JSONException
    {
    	return User.jsons2objs(response.asJSONObject().getJSONArray("users"));
    }
	
	public static int parseForFriendsRelationship(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("relationship");
	}

	public static UserBase[] parseForFriendsMutual(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return UserBase.jsons2objs(json.getJSONArray("data"));
	}
	
	public static int parseForFriendsAdd(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	/*--------------------------------------------App-------------------------------------*/
	public static AppStatus[] parseForAppStatus(Response response) throws KxException, JSONException
	{
		return AppStatus.jsons2objs(response.asJSONObject().getJSONArray("statuses"));
	}
	
	public static AppUids parseForAppFriends(Response response) throws KxException, JSONException
	{
		return new AppUids(response);
	}
	
	public static AppStatus[] parseForAppInvited(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json, paging);
		return AppStatus.jsons2objs(json.getJSONArray("statuses"));
	}
	
	public static Rate_limit_status parseForAppRate_limit_status(Response response) throws KxException, JSONException
	{
		return new Rate_limit_status(response);
	}
	
	/*--------------------------------------------Records-------------------------------------*/
	public static long parseForRecordsAdd(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("rid");
	}
	
	public static Record[] parseForRecordsMe(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return Record.jsons2objs(json.getJSONArray("data"));
	}
	
	public static Record[] parseForRecordsFriends(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return Record.jsons2objs(json.getJSONArray("data"));
	}
	
	public static Record[] parseForRecordsPublic(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return Record.jsons2objs(json.getJSONArray("data"));
	}
	
	public static Record[] parseForRecordsUser(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return Record.jsons2objs(json.getJSONArray("data"));
	}
	
	public static Record[] parseForRecordsSearch(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return Record.jsons2objs(json.getJSONArray("data"));
	}
	
	public static TopicRecord[] parseForRecordsTopics(Response response) throws KxException, JSONException
	{
		return TopicRecord.jsons2objs(response.asJSONArray());
	}
	
	public static long parseForRecordsDelete(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("result");
	}
	
	
	/*--------------------------------------------Album-------------------------------------*/
	public static long parseForAlbumCreate(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("albumid");
	}
	
	public static AlbumModel[] parseForAlbumShow(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		return AlbumModel.jsons2objs(response.asJSONObject().getJSONArray("data"));
	}
	
	
	/*--------------------------------------------Photo-------------------------------------*/
	public static PhotoUploaded parseForPhotoUpload(Response response) throws KxException, JSONException
	{
		return new PhotoUploaded(response);
	}

	public static PhotoModel[] parseForPhotoShow(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return PhotoModel.jsons2objs(json.getJSONArray("data"));
	}

	public static PhotoLatest[] parseForPhotoLatest(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return PhotoLatest.jsons2objs(json.getJSONArray("data"));
	}
	
	
	/*--------------------------------------------GameFriends-------------------------------------*/
	public static int parseForGameFriendsCreate(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static int parseForGameFriendsChange_privacy(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static int parseForGameFriendsRequest(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static GFRequst[] parseForGameFriendsRequest_list(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return GFRequst.jsons2objs(json.getJSONArray("users"));
	}
	
	public static int parseForGameFriendsDeal_request(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static GFUser[] parseForGameFriendsFriends(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return GFUser.jsons2objs(json.getJSONArray("users"));
	}
	
	public static GFUser[] parseForGameFriendsSearch(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return GFUser.jsons2objs(json.getJSONArray("users"));
	}
	
	public static GFUser[] parseForGameFriendsShow(Response response) throws KxException, JSONException
	{
		return GFUser.jsons2objs(response.asJSONObject().getJSONArray("users"));
	}
	
	
	/*--------------------------------------------Message-------------------------------------*/
	public static long parseForMessageSend(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("mid");
	}
	
	public static MessageModel[] parseForMessageInbox(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return MessageModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static MessageModel[] parseForMessageOutbox(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return MessageModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static long parseForMessageReply(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("mid");
	}
	
	public static MessageModel[] parseForMessageDetail(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return MessageModel.jsons2objs(json.getJSONArray("data"));
	}
	

	/*--------------------------------------------Emotion-------------------------------------*/
	public static EmotionModel[] parseForEmotionShow(Response response) throws KxException, JSONException
	{
		return EmotionModel.jsons2objs(response.asJSONArray());
	}
	
	
	/*--------------------------------------------Rgroup-------------------------------------*/
	public static long parseForRgroupGroup_create(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("gid");
	}
	
	public static Group[] parseForRgroupGroups(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return Group.jsons2objs(json.getJSONArray("data"));
	}
	
	public static GroupNew[] parseForRgroupTalks(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return GroupNew.jsons2objs(json.getJSONArray("data"));
	}
	
	public static long parseForRgroupTalk_create(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("tid");
	}
	
	public static TalkDetail parseForRgroupTalk_detail(Response response) throws KxException, JSONException
	{
		return new TalkDetail(response);
	}
	
	public static RgroupUser[] parseForRgroupMembers(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return RgroupUser.jsons2objs(json.getJSONArray("data"));
	}
	
	public static GroupNotice[] parseForRgroupNotices(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		if(response.asString().length() > 0){
			JSONObject json = response.asJSONObject();
			getPaging(json.getJSONObject("paging"), paging);
			return GroupNotice.jsons2objs(json.getJSONArray("data"));
		}
		else 
			return new GroupNotice[0];
	}
	
	public static GroupPhoto[] parseForRgroupPhotos(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return GroupPhoto.jsons2objs(json.getJSONArray("data"));
	}
	
	public static GroupPhotoDetail parseForRgroupPhoto_detail(Response response) throws KxException, JSONException
	{
		return new GroupPhotoDetail(response);
	}
	
	/*--------------------------------------------Like-------------------------------------*/
	public static LikeResult parseForLikeCreate(Response response) throws KxException, JSONException
	{
		return new LikeResult(response);
	}
	
	public static LikeResult parseForLikeCancel(Response response) throws KxException, JSONException
	{
		return new LikeResult(response);
	}
	
	public static LikeStatus[] parseForLikeCheck(Response response) throws KxException, JSONException
	{
		return LikeStatus.jsons2objs(response.asJSONObject().getJSONArray("data"));
	}
	
	public static LikeUser[] parseForLikeShow(Response response, int[] total_num) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		total_num[0] = json.getInt("total_num");
		return LikeUser.jsons2objs(json.getJSONArray("data"));
	}
	
	
	/*--------------------------------------------Comment-------------------------------------*/
	public static long parseForCommentCreate(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getJSONObject("data").getLong("thread_cid");
	}
	
	public static long parseForCommentReply(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getJSONObject("data").getLong("cid");
	}
	
	public static CommentModel[] parseForCommentList(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return CommentModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static CommentWrap[] parseForCommentComment_list(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return CommentWrap.jsons2objs(json.getJSONArray("data"));
	}
	
	public static CommentWrap[] parseForCommentReply_list(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return CommentWrap.jsons2objs(json.getJSONArray("data"));
	}
	
	public static int parseForCommentDelete(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getJSONObject("data").getInt("result");
	}
	
	
	/*--------------------------------------------Repaste-------------------------------------*/
	public static int parseForRepasteCreate(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static RepasteModel[] parseForRepasteMe(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return RepasteModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static OtherRepaste[] parseForRepasteFriends(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return OtherRepaste.jsons2objs(json.getJSONArray("data"));
	}
	
	public static OtherRepaste[] parseForRepastePublic(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return OtherRepaste.jsons2objs(json.getJSONArray("data"));
	}
	
	public static RepasteModel[] parseForRepasteUser(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return RepasteModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static RepasteDetail parseForRepasteShow(Response response) throws KxException, JSONException
	{
		return new RepasteDetail(response);
	}
	
	public static int parseForRepasteAddtag(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static int parseForRepasteInteract(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	/*--------------------------------------------MsgCenter-------------------------------------*/
	public static MsgNewNum parseForMsgCenterSummary(Response response) throws KxException, JSONException
	{
		return new MsgNewNum(response);
	}
	
	public static int parseForMsgCenterClear(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	
	/*--------------------------------------------Diary-------------------------------------*/
	public static long parseForDiaryDiary_create(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getJSONObject("did") .getLong("result");
	}
	
	public static DiaryModel[] parseForDiaryDiary_me(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return DiaryModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static OtherDiary[] parseForDiaryDiary_friends(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return OtherDiary.jsons2objs(json.getJSONArray("data"));
	}
	
	public static OtherDiary[] parseForDiaryDiary_tag(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return OtherDiary.jsons2objs(json.getJSONArray("data"));
	}
	
	public static OtherDiary[] parseForDiaryDiary_user(Response response, Paging paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return OtherDiary.jsons2objs(json.getJSONArray("data"));
	}
	
	public static DiaryDetail parseForDiaryDiary_show(Response response) throws KxException, JSONException
	{
		return new DiaryDetail(response);
	}
	
	
	/*--------------------------------------------Page-------------------------------------*/
	public static int parseForPageAddfan(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	
	/*--------------------------------------------Board-------------------------------------*/
	public static long parseForBoardCreate(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("thread_bid");
	}
	
	public static BoardModel[] parseForBoardMe(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return BoardModel.jsons2objs(json.getJSONArray("threads"));
	}
	
	public static BoardModel[] parseForBoardUser(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return BoardModel.jsons2objs(json.getJSONArray("threads"));
	}
	
	public static BoardOther[] parseForBoardReply_list(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return BoardOther.jsons2objs(json.getJSONArray("threads"));
	}
	
	public static int parseForBoardDelete(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getJSONObject("data").getInt("result");
	}
	
	
	/*--------------------------------------------Feed-------------------------------------*/
	public static int parseForFeedSend(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static FeedModel[][] parseForFeedUser(Response response, int[] total) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		total[0] = json.getInt("total");
		JSONArray jsons = json.getJSONArray("data");
		FeedModel[][] feeds = new FeedModel[jsons.length()][];
		for(int i=0; i<jsons.length(); i++)
		{
			feeds[i] = FeedModel.jsons2objs(jsons.getJSONArray(i));
		}
		return feeds;
	}
	
	
	/*--------------------------------------------Sysnews-------------------------------------*/
	public static int parseForSysnewsSend(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static int parseForSysnewsInvitation(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	/*--------------------------------------------Forward-------------------------------------*/
	public static long parseForForwardCreate(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("fid");
	}
	
	public static long parseForForwardReforward(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("fid");
	}
	
	public static ForwardDetail[] parseForForwardList(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json, paging);
		return ForwardDetail.jsons2objs(json.getJSONArray("forwardlist"));
	}
	
	public static ForwardModel[] parseForForwardMe(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json, paging);
		return ForwardModel.jsons2objs(json.getJSONArray("forwardlist"));
	}
	
	/*--------------------------------------------Lbs-------------------------------------*/
	public static long parseForLbsCheckin(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("chid");
	}
	
	public static LBS parseForLbsMe(Response response) throws KxException, JSONException
	{
		return new LBS(response);
	}
	
	public static FPlace[] parseForLbsFriend(Response response) throws KxException, JSONException
	{
		return FPlace.jsons2objs(response.asJSONArray());
	}
	
	public static LBSFriend[] parseForLbsNearfriends(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json, paging);
		if(json.getString("lbslist").length() > 0){
			return LBSFriend.jsons2objs(json.getJSONArray("lbslist"));
		}
		return new LBSFriend[0];
	}
	
	public static LBSFriend[] parseForLbsNearusers(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json, paging);
		return LBSFriend.jsons2objs(json.getJSONArray("lbslist"));
	}
	
	public static LBSPhoto[] parseForLbsPhoto(Response response) throws KxException, JSONException
	{
		return LBSPhoto.jsons2objs(response.asJSONArray());
	}
	
	public static String parseForLbsCreate(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getString("place_id");
	}
	
	
	/*--------------------------------------------Film-------------------------------------*/
	public static FilmModel[] parseForFilmSearch(Response response, int[] paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		paging[0] = json.getInt("total");
		paging[1] = json.getInt("start");
		paging[2] = json.getInt("prev");
		paging[3] = json.getInt("next");
		return FilmModel.jsons2objs(json.getJSONObject("list"));
	}
	
	public static int parseForFilmWatched(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static int parseForFilmWish(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static FilmWish[] parseForFilmWishlist(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return FilmWish.jsons2objs(json.getJSONArray("data"));
	}
	
	public static FilmRecommend[] parseForFilmRecommendlist(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return FilmRecommend.jsons2objs(json.getJSONArray("data"));
	}
	
	public static FilmWatched[] parseForFilmSawlist(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return FilmWatched.jsons2objs(json.getJSONArray("data"));
	}
	
	public static int parseForFilmRecommend(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static FilmOther[] parseForFilmFriendtouch(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return FilmOther.jsons2objs(json.getJSONArray("data"));
	}
	
	/*--------------------------------------------GameNotice-------------------------------------*/
	public static int parseForGameNoticeSet(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static int parseForGameNoticeUpdate(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	
	/*--------------------------------------------Vote-------------------------------------*/
	public static int parseForVoteReply(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static long parseForVotePost(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getLong("vid");
	}
	
	public static VoteModel[] parseForVoteFeed(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return VoteModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static VoteModel[] parseForVoteFriend(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return VoteModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static VoteModel[] parseForVoteMypost(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return VoteModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static VoteModel[] parseForVoteMe(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return VoteModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static VoteDetail parseForVoteDetail(Response response) throws KxException, JSONException
	{
		return new VoteDetail(response);
	}
	
	public static VoteModel[] parseForVoteNewlist(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return VoteModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static VoteModel[] parseForVoteHot(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		return VoteModel.jsons2objs(json.getJSONArray("data"));
	}
	
	
	/*--------------------------------------------Gift-------------------------------------*/
	public static GiftModel[] parseForGiftList(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		paging.total = json.getInt("total");
		return GiftModel.jsons2objs(json.getJSONArray("data"));
	}
	
	public static int parseForGiftSendgift(Response response) throws KxException, JSONException
	{
		return response.asJSONObject().getInt("result");
	}
	
	public static GiftRev[] parseForGiftReceived(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		paging.total = json.getInt("total");
		return GiftRev.jsons2objs(json.getJSONArray("data"));
	}
	
	public static GiftRev[] parseForGiftDelivered(Response response, PagingWithTotal paging) throws KxException, JSONException
	{
		JSONObject json = response.asJSONObject();
		getPaging(json.getJSONObject("paging"), paging);
		paging.total = json.getInt("total");
		return GiftRev.jsons2objs(json.getJSONArray("data"));
	}
}