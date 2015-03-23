package kx2_4j.model.repaste;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class RepasteVote {
	private int voteitem;		//某个投票选项的ID
	private String name;		//某个投票选项的投票人的姓名
	private long num;		//某个投票选项的投票数量
	private String percentage;		//某个投票选项的百分比(占总投票数的比例)
	
    public RepasteVote(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		voteitem = json.getInt("voteitem");
				name = json.getString("name");
				num = json.getLong("num");
				percentage = json.getString("percentage");
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public RepasteVote(JSONObject json) throws KxException {
        super();
        init(json);
    }

    public RepasteVote(){
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
        RepasteVote other = (RepasteVote) obj;
        
		if (voteitem != other.voteitem) {
            return false;
        }
        
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "voteitem=" + voteitem
				+ ", name=" + name
				+ ", num=" + num
				+ ", percentage=" + percentage
				+ '}';
	}
	
	
	public int getVoteitem()
	{
		return voteitem;
	}
	
	public String getName()
	{
		return name;
	}
	
	public long getNum()
	{
		return num;
	}
	
	public String getPercentage()
	{
		return percentage;
	}
	

    
    public static RepasteVote[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new RepasteVote[0];
    	}
    	int len = jsons.length();
    	RepasteVote[] objs = new RepasteVote[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new RepasteVote(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}