package kx2_4j.model.film;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FilmRecommend {
	private long mid;		//电影id
	private String cname;		//电影名字
	private String ename;		//电影英文标题
	private String mtype;		//电影类型
	private String cover;		//电影缩略图
	private int year;		//年份
	private String zone;		//地区
	private String reason;		//推荐理由
	private String viewfriends;		//看过该电影的好友（多个uid用“,”隔开）
	private String wantfriends;		//想看改电影的好友（多个uid用“,”隔开
	
    public FilmRecommend(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		mid = json.getLong("mid");
				cname = json.getString("cname");
				ename = json.getString("ename");
				mtype = json.getString("mtype");
				cover = json.getString("cover");
				year = json.getInt("year");
				zone = json.getString("zone");
				reason = json.getString("reason");
				viewfriends = json.getString("viewfriends");
				wantfriends = json.getString("wantfriends");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FilmRecommend(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FilmRecommend(){
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
        FilmRecommend other = (FilmRecommend) obj;
        
		if (mid != other.mid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "mid=" + mid
				+ ", cname=" + cname
				+ ", ename=" + ename
				+ ", mtype=" + mtype
				+ ", cover=" + cover
				+ ", year=" + year
				+ ", zone=" + zone
				+ ", reason=" + reason
				+ ", viewfriends=" + viewfriends
				+ ", wantfriends=" + wantfriends
				+ '}';
	}
	
	
	public long getMid()
	{
		return mid;
	}
	
	public String getCname()
	{
		return cname;
	}
	
	public String getEname()
	{
		return ename;
	}
	
	public String getMtype()
	{
		return mtype;
	}
	
	public String getCover()
	{
		return cover;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public String getZone()
	{
		return zone;
	}
	
	public String getReason()
	{
		return reason;
	}
	
	public String getViewfriends()
	{
		return viewfriends;
	}
	
	public String getWantfriends()
	{
		return wantfriends;
	}
	

    
    public static FilmRecommend[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new FilmRecommend[0];
    	}
    	int len = jsons.length();
    	FilmRecommend[] objs = new FilmRecommend[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new FilmRecommend(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}