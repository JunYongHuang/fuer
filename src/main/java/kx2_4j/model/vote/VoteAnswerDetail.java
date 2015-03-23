package kx2_4j.model.vote;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class VoteAnswerDetail {
	private int ordernum;		//投票选项序列号
	private String answer;		//投票选项内容
	private long votenum;		//选项获得的投票数
	private String ctime;		//创建时间
	
    public VoteAnswerDetail(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		ordernum = json.getInt("ordernum");
				answer = json.getString("answer");
				votenum = json.getLong("votenum");
				ctime = json.getString("ctime");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public VoteAnswerDetail(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public VoteAnswerDetail(){
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
        VoteAnswerDetail other = (VoteAnswerDetail) obj;
        
		if (ordernum != other.ordernum) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "ordernum=" + ordernum
				+ ", answer=" + answer
				+ ", votenum=" + votenum
				+ ", ctime=" + ctime
				+ '}';
	}
	
	
	public int getOrdernum()
	{
		return ordernum;
	}
	
	public String getAnswer()
	{
		return answer;
	}
	
	public long getVotenum()
	{
		return votenum;
	}
	
	public String getCtime()
	{
		return ctime;
	}
}