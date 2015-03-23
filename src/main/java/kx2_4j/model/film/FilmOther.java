package kx2_4j.model.film;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class FilmOther {
	private long mid;		//电影id
	private String cname;		//电影名字
	private String ename;		//电影英文标题
	private String mtype;		//电影类型
	private String cover;		//电影缩略图
	private int score;		//评分分数
	private String comment;		//影评
	private long uid;		//好友uid
	private int statue;		//好友影片状态（0想看 1看过）
	
    public FilmOther(Response res) throws KxException {
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
				score = json.getInt("score");
				comment = json.getString("comment");
				uid = json.getLong("uid");
				statue = json.getInt("statue");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public FilmOther(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public FilmOther(){
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
        FilmOther other = (FilmOther) obj;
        
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
				+ ", score=" + score
				+ ", comment=" + comment
				+ ", uid=" + uid
				+ ", statue=" + statue
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
	
	public int getScore()
	{
		return score;
	}
	
	public String getComment()
	{
		return comment;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public int getStatue()
	{
		return statue;
	}
	

    
    public static FilmOther[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new FilmOther[0];
    	}
    	int len = jsons.length();
    	FilmOther[] objs = new FilmOther[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new FilmOther(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}