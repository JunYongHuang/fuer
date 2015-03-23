package kx2_4j.model.vote;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class VoteAnswer {
	private int ordernum;		//投票选项序列号
	private String answer;		//投票选项内容
	
    public VoteAnswer(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		ordernum = json.getInt("ordernum");
				answer = json.getString("answer");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public VoteAnswer(JSONObject json) throws KxException {
        super();
        init(json);
    }
    
    public VoteAnswer(JSONObject json,String numkey) throws JSONException{
    	this.ordernum = Integer.parseInt(numkey);
    	this.answer = json.getString(numkey);
    }
    
    public VoteAnswer(JSONArray list,int index) throws JSONException{
    	this.ordernum = index;
    	this.answer = list.getString(index);
    }

    public VoteAnswer(){
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
        VoteAnswer other = (VoteAnswer) obj;
        
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
}