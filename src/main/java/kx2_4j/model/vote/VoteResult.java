package kx2_4j.model.vote;


import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.records.RecordUser;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class VoteResult {
	private RecordUser userinfo;		//用户信息
	private Integer[]	vote;
	private String ctime;		//投票时间
	
    public VoteResult(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		userinfo = new RecordUser(json.getJSONObject("userinfo"));
				ctime = json.getString("ctime");
				if(!json.isNull("vote"))
				{
					String svote = json.getString("vote");
					String[] savote = svote.split(",");
					vote = new Integer[savote.length];
					for(int i=0; i<savote.length; i++)
					{
						vote[i] = Integer.parseInt(savote[i]);
					}
				}
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public VoteResult(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public VoteResult(){
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
        VoteResult other = (VoteResult) obj;
        
		if (userinfo != other.userinfo) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "userinfo=" + userinfo
				+ ", vote=" + vote 
				+ ", ctime=" + ctime
				+ '}';
	}
	
	public RecordUser getUserinfo()
	{
		return userinfo;
	}
	
	public Integer[] getVote()
	{
		return vote;
	}
	
	public String getCtime()
	{
		return ctime;
	}
}