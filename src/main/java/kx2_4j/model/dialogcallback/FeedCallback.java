package kx2_4j.model.dialogcallback;

import java.util.Map;

public class FeedCallback extends DialogCallback{
	private long feedid;	//动态ID
	public FeedCallback(Map<String,String[]> params)
	{
		super(params);
		feedid = Long.parseLong(params.get("feedid")[0]);
	}
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", ctime=" + ctime
				+ ", sig=" + sig
				+ ", callbackkey=" + callbackkey
				+ ", feedid=" + feedid
				+ '}';
	}

	public long getFeedid()
	{
		return feedid;
	}
}