package kx2_4j.model.repaste;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RepasteModel {
	private String ctime;		//转贴创建时间
	private long uid;		//当前转贴人的的UID
	private long urpid;		//当前用户转贴唯一id, 与uid共同构成唯一转贴
	private long first_uid;		//第一转贴人的uid
	private String first_name;		//第一转贴人的姓名
	private String title;		//转贴标题
	private String summary;		//转贴摘要
	private String source_url;		//转贴内的视频或其他资源的地址
	private String repaste_type;		//转贴类型，包括网页、链接、日记、相册)
	private String img_src;		//转贴的封面图片(照片、照片专辑、视频转帖会返回)
	private int photo_num;		//照片数量（照片或照片专辑转帖会返回）
	
    public RepasteModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		ctime = json.getString("ctime");
				uid = json.getLong("uid");
				urpid = json.getLong("urpid");
				first_uid = json.getLong("first_uid");
				first_name = json.getString("first_name");
				title = json.getString("title");
				summary = json.getString("summary");
				source_url = json.getString("source_url");
				repaste_type = json.getString("repaste_type");
				img_src = json.getString("img_src");
				photo_num = json.getInt("photo_num");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RepasteModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public RepasteModel(){
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
        RepasteModel other = (RepasteModel) obj;
        
		if (uid != other.uid) {
            return false;
        }
        
		else if (urpid != other.urpid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "ctime=" + ctime
				+ ", uid=" + uid
				+ ", urpid=" + urpid
				+ ", first_uid=" + first_uid
				+ ", first_name=" + first_name
				+ ", title=" + title
				+ ", summary=" + summary
				+ ", source_url=" + source_url
				+ ", repaste_type=" + repaste_type
				+ ", img_src=" + img_src
				+ ", photo_num=" + photo_num
				+ '}';
	}
	
	
	public String getCtime()
	{
		return ctime;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public long getUrpid()
	{
		return urpid;
	}
	
	public long getFirst_uid()
	{
		return first_uid;
	}
	
	public String getFirst_name()
	{
		return first_name;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getSummary()
	{
		return summary;
	}
	
	public String getSource_url()
	{
		return source_url;
	}
	
	public String getRepaste_type()
	{
		return repaste_type;
	}
	
	public String getImg_src()
	{
		return img_src;
	}
	
	public int getPhoto_num()
	{
		return photo_num;
	}
    
    public static RepasteModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new RepasteModel[0];
    	}
    	int len = jsons.length();
    	RepasteModel[] objs = new RepasteModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new RepasteModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}