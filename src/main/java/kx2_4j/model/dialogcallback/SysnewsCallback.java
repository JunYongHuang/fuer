package kx2_4j.model.dialogcallback;

import java.util.Map;

public class SysnewsCallback extends DialogCallback{
	private int num;	//接收系统消息或邀请用户的数量
	private String fuids;	//接收系统消息或邀请的用户的uid，以逗号隔开

	public SysnewsCallback(Map<String,String[]> params)
	{
		super(params);
		num = Integer.parseInt(params.get("num")[0]);
		fuids = params.get("fuids")[0];
	}
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", ctime=" + ctime
				+ ", sig=" + sig
				+ ", callbackkey=" + callbackkey
				+ ", num=" + num
				+ ", fuids=" + fuids
				+ '}';
	}

	public int getNum()
	{
		return num;
	}
	public String getFuids()
	{
		return fuids;
	}
}