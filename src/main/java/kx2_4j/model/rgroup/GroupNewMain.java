
package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class GroupNewMain {
	private String txt;		//文字内容
	private int type;		//动态内容类型。0/1/2/3/4：普通文本/@人/URL/视频/音频
	private String title;		//视频标题
	private String img_url;		//视频图片url
	private String swf_url;		//视频swf地址
	
    public GroupNewMain(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		txt = json.getString("txt");
				type = json.getInt("type");
				title = json.getString("title");
				img_url = json.getString("img_url");
				swf_url = json.getString("swf_url");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public GroupNewMain(JSONObject json) throws KxException {
        super();
        init(json);
    }

	public GroupNewMain() {
		// TODO Auto-generated constructor stub
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
        GroupNewMain other = (GroupNewMain) obj;
        
		if (txt != other.txt) {
            return false;
        }
        
		else if (type != other.type) {
            return false;
        }
        
		else if (title != other.title) {
            return false;
        }
        
		else if (img_url != other.img_url) {
            return false;
        }
        
		else if (swf_url != other.swf_url) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "txt=" + txt
				+ ", type=" + type
				+ ", title=" + title
				+ ", img_url=" + img_url
				+ ", swf_url=" + swf_url
				+ '}';
	}
	
	
	public String getTxt()
	{
		return txt;
	}
	
	public int getType()
	{
		return type;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getImg_url()
	{
		return img_url;
	}
	
	public String getSwf_url()
	{
		return swf_url;
	}
    
    public static GroupNewMain[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new GroupNewMain[0];
    	}
    	int len = jsons.length();
    	GroupNewMain[] objs = new GroupNewMain[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new GroupNewMain(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}