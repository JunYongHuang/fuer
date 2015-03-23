package kx2_4j.model.forward;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class ForwardModel {
	private String objtype;		//资源类型
	private long ouid;		//资源所有者
	private String name;		//资源所有者姓名
	private String content;		//转发附言内容
	private String title;		//标题
	private String abstra;		//摘要
	private String link;		//资源链接
	private String img;		//缩略图
	
    public ForwardModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
				objtype = json.getString("objtype");
        		ouid = json.getLong("ouid");
				name = json.getString("name");
				content = json.getString("content");
				title = json.getString("title");
				abstra = json.getString("abstract");
				link = json.getString("link");
				img = json.getString("img");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public ForwardModel(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public ForwardModel(){
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
        ForwardModel other = (ForwardModel) obj;
        
		if (ouid != other.ouid) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "objtype=" + objtype
				+ ", ouid=" + ouid
				+ ", name=" + name
				+ ", content=" + content
				+ ", title=" + title
				+ ", abstract=" + abstra
				+ ", link=" + link
				+ ", img=" + img
				+ '}';
	}

	
	public String getObjtype()
	{
		return objtype;
	}
	
	public long getOuid()
	{
		return ouid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getAbstract()
	{
		return abstra;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public String getImg()
	{
		return img;
	}

    public static ForwardModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new ForwardModel[0];
    	}
    	int len = jsons.length() - 1;
    	ForwardModel[] objs = new ForwardModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new ForwardModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}