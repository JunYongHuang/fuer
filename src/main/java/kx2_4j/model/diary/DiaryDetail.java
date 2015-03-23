package kx2_4j.model.diary;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class DiaryDetail {
	private long uid;		//用户ID
	private long did;		//日记ID
	private String title;		//日记标题
	private String content;		//日记摘要
	private String ctime;		//日记创建时间
	private String tag;		//提到的好友，格式：用户UID:姓名，多个好友用半角逗号分隔 如：1111:张三,1112:李四。只有当前用户的日记才会返回
	private int privacy;		//权限设置 只有当前用户的日记才会返回
	private int draft;		//是否草稿 1:草稿 0:不是草稿 只有当前用户的日记才会返回
	private String category;		//分类名称 只有当前用户的日记才会返回
	
    public DiaryDetail(Response res) throws KxException, JSONException {
        super();
        init(res.asJSONObject().getJSONObject("data"));
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		uid = json.getLong("uid");
				did = json.getLong("did");
				title = json.getString("title");
				content = json.getString("content");
				ctime = json.getString("ctime");
				tag = json.getString("tag");
				privacy = json.getInt("privacy");
				draft = json.getInt("draft");
				category = json.getString("category");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public DiaryDetail(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public DiaryDetail(){
    }
	
	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DiaryDetail other = (DiaryDetail) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
		else if (did != other.did) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", did=" + did
				+ ", title=" + title
				+ ", content=" + content
				+ ", ctime=" + ctime
				+ ", tag=" + tag
				+ ", privacy=" + privacy
				+ ", draft=" + draft
				+ ", category=" + category
				+ '}';
	}
	
	
	public long getUid()
	{
		return uid;
	}
	
	public long getDid()
	{
		return did;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	public int getPrivacy()
	{
		return privacy;
	}
	
	public int getDraft()
	{
		return draft;
	}
	
	public String getCategory()
	{
		return category;
	}
}