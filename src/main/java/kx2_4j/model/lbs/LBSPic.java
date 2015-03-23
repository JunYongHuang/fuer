package kx2_4j.model.lbs;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class LBSPic {
	private String pic_cover;		//缩略图
	private String pic_large;		//大图
	private String ctime;		//签到时间
	private String content;		//签到附言
	
    public LBSPic(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		pic_cover = json.getString("pic_cover");
				pic_large = json.getString("pic_large");
				ctime = json.getString("ctime");
				content = json.getString("content");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public LBSPic(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public LBSPic(){
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
        LBSPic other = (LBSPic) obj;
        
		if (pic_cover != other.pic_cover) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "pic_cover=" + pic_cover
				+ ", pic_large=" + pic_large
				+ ", ctime=" + ctime
				+ ", content=" + content
				+ '}';
	}
	
	
	public String getPic_cover()
	{
		return pic_cover;
	}
	
	public String getPic_large()
	{
		return pic_large;
	}
	
	public String getCtime()
	{
		return ctime;
	}
	
	public String getContent()
	{
		return content;
	}
    
    public static LBSPic[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new LBSPic[0];
    	}
    	int len = jsons.length();
    	LBSPic[] objs = new LBSPic[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new LBSPic(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}