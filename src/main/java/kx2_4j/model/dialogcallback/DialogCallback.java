package kx2_4j.model.dialogcallback;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import kx2_4j.KxConfig;
import kx2_4j.KxUtils;
import kx2_4j.http.HttpClient;
import kx2_4j.http.PostParameter;

public class DialogCallback {
	protected long uid;	//执行当前操作的用户UID
	protected long ctime;	//回调时间
	protected String sig;	//数据签名。用来判断请求是否由开心网所发出（与API1.2验证方法不同）
	protected String callbackkey;	//用户操作的代码。具体的代码所代表含义请参见以下操作代码对照表。 
	public static String CONSUMER_SECRET = KxConfig.getValue("CONSUMER_SECRET");//secret key
	/*sysnews	系统消息
	invite	邀请好友
	newsfeed	好友动态
	pay	支付*/
	
	
	public DialogCallback(Map<String,String[]> params)
	{
		uid = Long.parseLong(params.get("uid")[0]);
		ctime = Long.parseLong(params.get("ctime")[0]);
		sig = params.get("sig")[0];
		callbackkey = params.get("callbackkey")[0];
	}
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", ctime=" + ctime
				+ ", sig=" + sig
				+ ", callbackkey=" + callbackkey
				+ '}';
	}
	
	
	public static boolean checkSig(Map<String,String[]> params)
	{
		if(!params.containsKey("sig"))return false;
		Iterator<Entry<String,String[]>> iter = params.entrySet().iterator(); 
		PostParameter[] paramsns = new PostParameter[params.size()-1];
		String sig_raw = "";
		int i=0;
		while (iter.hasNext()) { 
			Entry<String,String[]> entry = iter.next(); 
		    String key = entry.getKey(); 
		    String[] val = entry.getValue(); 
		    if(!key.equals("sig")){
		    	paramsns[i++] = new PostParameter(key,val[0]);
		    }
		    else
		    {
		    	sig_raw = val[0];
		    }
		}
		Arrays.sort(paramsns);
		String para = HttpClient.encodeParameters(paramsns);
		String sig = KxUtils.getMD5Str(para+CONSUMER_SECRET);
		System.out.println("计算出的签名："+sig);
		return sig.equals(sig_raw);
	}
    
    public Long getUid()
	{
		return uid;
	}
	public Long getCtime()
	{
		return ctime;
	}
	public String getSig()
	{
		return sig;
	}
	public String getCallbackkey()
	{
		return callbackkey;
	}
}