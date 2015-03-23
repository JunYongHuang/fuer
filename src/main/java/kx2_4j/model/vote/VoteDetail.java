package kx2_4j.model.vote;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class VoteDetail extends VoteModel {
	private VoteAnswerDetail[] answers;		//投票选项
	private VoteResult result;
	
    public VoteDetail(Response res) throws KxException, JSONException {
        super(res);
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException, JSONException {
        if (json != null) {
        	//String answer = json.getString("answers");
			//if(answer.startsWith("{"))
			{
				JSONObject list = json.getJSONObject("answers");
				answers = new VoteAnswerDetail[list.length()];
				String[] ites = JSONObject.getNames(list);
				for(int i=0;i<ites.length;i++)
	 		    {
	 			    answers[i] = new VoteAnswerDetail(list.getJSONObject(ites[i]));
	 		    }
			}/*
			if(answer.startsWith("["))
			{
				JSONArray list = json.getJSONArray("answers");
				answers = new ArrayList<VoteAnswerDetail>(list.length());
				for(int i=0;i<list.length();i++)
	 		    {
	 			    answers.add(new VoteAnswerDetail(list.getJSONObject(i)));
	 		    }
			}*/
			result = new VoteResult(json.getJSONObject("myVoteResult"));
        }
    }
    
    public VoteDetail(JSONObject json) throws KxException, JSONException {
        super(json);
        init(json);
    }

    public VoteDetail(){
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "vid=" + getVid()
				+ ", userinfo=" + getUserinfo()
				+ ", topic=" + getTopic()
				+ ", votenum=" + getVotenum()
				+ ", ctime=" + getCtime()
				+ ", endtime=" + getEndtime()
				+ ", intro=" + getIntro()
				+ ", maxnum=" + getMaxnum()
				+ ", commentnum=" + getCommentnum()
				+ ", glimit=" + getGlimit()
				+ ", mtime=" + getMtime()
				+ ", onlyfriend=" + getOnlyfriend()
				+ ", answers=" + answers
				+ ", result=" + result
				+ '}';
	}
	
	
	public VoteAnswerDetail[] getAnswersDetail()
	{
		return answers;
	}
	
	public VoteResult getMyVote()
	{
		return result;
	}
}