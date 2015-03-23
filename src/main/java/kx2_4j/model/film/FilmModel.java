package kx2_4j.model.film;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FilmModel {
	private long mid;		//电影id
	private String cname;		//电影中文名字
	private String mtype;		//电影类型
	private String ename;		//电影英文名字
	private int year;		//电影上映年份
	private String zone;		//地区
	private String director;		//导演
	private String actor;		//演员
	private String intro;		//剧情简介
	private String cover;		//电影缩略图
	private int score;		//评分分数
	private long viewuser;		//看过人数
	private long friendview;		//好友看过人数
	
    public FilmModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		mid = json.getLong("mid");
				cname = json.getString("cname");
				mtype = json.getString("mtype");
				ename = json.getString("ename");
				year = json.getInt("year");
				zone = json.getString("zone");
				director = json.getString("director");
				actor = json.getString("actor");
				intro = json.getString("intro");
				cover = json.getString("cover");
				score = json.getInt("score");
				viewuser = json.getLong("viewuser");
				friendview = json.getLong("friendview");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FilmModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FilmModel(){
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
        FilmModel other = (FilmModel) obj;
        
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
				+ ", mtype=" + mtype
				+ ", ename=" + ename
				+ ", year=" + year
				+ ", zone=" + zone
				+ ", director=" + director
				+ ", actor=" + actor
				+ ", intro=" + intro
				+ ", cover=" + cover
				+ ", score=" + score
				+ ", viewuser=" + viewuser
				+ ", friendview=" + friendview
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
	
	public String getMtype()
	{
		return mtype;
	}
	
	public String getEname()
	{
		return ename;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public String getZone()
	{
		return zone;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public String getActor()
	{
		return actor;
	}
	
	public String getIntro()
	{
		return intro;
	}
	
	public String getCover()
	{
		return cover;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public long getViewuser()
	{
		return viewuser;
	}
	
	public long getFriendview()
	{
		return friendview;
	}
    
    public static FilmModel[] jsons2objs(JSONObject jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new FilmModel[0];
    	}
    	int len = jsons.length();
    	FilmModel[] objs = new FilmModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new FilmModel(jsons.getJSONObject(JSONObject.getNames(jsons)[i]));
    	}
    	return objs;
    }
}