package kx2_4j.model.rgroup;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class TalkDetail {
	private GroupTalk detail;		//会话发起内容
	private GroupTalk[] replys;		//回复
	
    public TalkDetail(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		detail = new GroupTalk(json.getJSONObject("detail"));
        		replys = GroupTalk.jsons2objs(json.getJSONObject("replys").getJSONArray("data"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public TalkDetail(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public TalkDetail(){
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
        TalkDetail other = (TalkDetail) obj;
        
		if (detail != other.detail) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "detail=" + detail
				+ ", replys=" + replys
				+ '}';
	}
	
	
	public GroupTalk getDetail()
	{
		return detail;
	}
	
	public GroupTalk[] getReplys()
	{
		return replys;
	}
}