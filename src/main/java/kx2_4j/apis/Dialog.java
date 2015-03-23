package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.KxConfig;
import kx2_4j.KxConfigDialog;
import kx2_4j.KxUtils;
import kx2_4j.http.HttpClient;
import kx2_4j.http.PostParameter;

public class Dialog extends KxAPIcaller{

    public Dialog(String token)
    {
    	super(token);
    }
    
	public static String baseURL = KxConfigDialog.getValue("baseURL");
	private String display="popup";		//dialog弹出框的展示方式	连接网站支持popup、page方式，手机客户端请使用wap方式
	private String redirect_uri = KxConfigDialog.getValue("redirect_uri");		//回调地址,支付接口除外
	private String app_id = KxConfigDialog.getValue("APP_ID");				//申请应用时获得的APPID（Display=wap时,无需填写）
	private String callback	=	"http://api.kaixin001.com/interface/testback.php";		//支付接口的回调地址，通知第三方支付已经成功。

	/**
	 * 发布好友动态，此接口为有交互界面的接口
	 * @param linktext 动态里面的链接文字，不超过15个汉字。例如，去帮忙
	 * @param link 动态里的链接地址，必须以http或https开头。
	 * @param text 发送动态所使用的文案，不超过60个汉字，否则会被截断。该文案可以有{_USER_} {_USER_TA_}变量，解析时会被替换为当前用户名字和他/她。例如：	{_USER_} 在做XX任务时遇到了强大的XX，快去帮帮{_USER_TA_}！
	 * @param picurl 发送动态所使用的图片地址，如果动态分享中需要发布图片，则此项必填。图片尺寸为80×80，否则会被压缩/拉伸为80×80
	 * @return
	 */
	public String getFeedUrl(String linktext,String link,String text,String picurl)
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>();
    	params.add(new PostParameter("linktext",linktext));
    	params.add(new PostParameter("link",link));
    	params.add(new PostParameter("text",text));
    	params.add(new PostParameter("picurl",picurl));
		return this.getDialogUrl("feed", params);
	}
	public String getFeedUrl(String linktext,String link,String text,String picurl,String display)
	{
		this.display = display;
		return this.getFeedUrl(linktext, link, text, picurl);
	}
	

	/**
	 * 发送邀请，此接口为有交互界面的接口
	 * @param text 发送系统消息所使用的文案，不超过60个汉字，否则会被截断。该文案可以有{_USER_} {_USER_TA_}变量，解析时会被替换为当前用户名字和他/她。例如：	{_USER_} 在做XX任务时遇到了强大的XX，快去帮帮{_USER_TA_}！
	 * @return
	 */
	public String getInvitationUrl(String text)
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>();
    	params.add(new PostParameter("text",text));
		return this.getDialogUrl("invitation", params);
	}
	public String getInvitationUrl(String text,String display)
	{
		this.display = display;
		return this.getInvitationUrl(text);
	}
	

	/**
	 * 发送系统消息，此接口为有交互界面的接口
	 * @param linktext 系统消息里面的链接文字，不超过15个汉字。例如，去帮忙
	 * @param link 系统消息里的链接地址，必须以http或https开头。
	 * @param text 发送系统消息所使用的文案，不超过60个汉字，否则会被截断。该文案可以有{_USER_} {_USER_TA_}变量，解析时会被替换为当前用户名字和他/她。例如：	{_USER_} 在做XX任务时遇到了强大的XX，快去帮帮{_USER_TA_}！
	 * @param picurl 发送系统消息所使用的图片地址，如果系统消息中需要发布图片，则此项必填。图片尺寸为80×80，否则会被压缩/拉伸为80×80
	 * @return
	 */
	public String getSysnewsUrl(String linktext,String link,String text,String picurl)
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>();
    	params.add(new PostParameter("linktext",linktext));
    	params.add(new PostParameter("link",link));
    	params.add(new PostParameter("text",text));
    	params.add(new PostParameter("picurl",picurl));
		return this.getDialogUrl("sysnews", params);
	}
	public String getSysnewsUrl(String linktext,String link,String text,String picurl,String display)
	{
		this.display = display;
		return this.getSysnewsUrl(linktext, link, text, picurl);
	}
    

	/**
	 * 发送支付请求，此接口为有交互界面的接口
	 * @param pname 商品名称。
	 * @param pnumber 商品数量。
	 * @param pcode 商品code
	 * @return
	 */
	public String getPayUrl(String pname,int pnumber,String pcode)
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>();
    	params.add(new PostParameter("pname",pname));
    	params.add(new PostParameter("pnumber",pnumber));
    	params.add(new PostParameter("pcode",pcode));
    	int amount = getPrice(pcode) * pnumber;	//价值开心币（与开心币的兑换关系）
    	String orderid = String.valueOf(Math.random());//己方生成的订单号，为将来对账用，请保证唯一
    	params.add(new PostParameter("amount",amount));
    	params.add(new PostParameter("orderid",orderid));
    	params.add(new PostParameter("callback",callback));
    	String sig = KxUtils.getMD5Str(""+amount+"&"+orderid+"&"+KxConfig.getValue("CONSUMER_SECRET"));
    	params.add(new PostParameter("sig",sig));
    	return this.getDialogUrl("pay", params);
	}
	public String getPayUrl(String pname,int pnumber,String pcode,String display)
	{
		this.display = display;
		return this.getPayUrl(pname, pnumber, pcode);
	}
	private int getPrice(String pcode)
	{
		return 1;
	}
	
    private String getDialogUrl(String api, ArrayList<PostParameter> params)
    {
    	api = baseURL + api;
    	params.add(new PostParameter("display",display));
    	params.add(new PostParameter("redirect_uri",redirect_uri));
    	if("wap".equals(display) || "iphone".equals(display))
    	{
    		api = api.replaceFirst("http", "https");
    		params.add(new PostParameter(this.tokenname,this.token));
    	}
    	else
    	{
    		params.add(new PostParameter("app_id",app_id));
    	}
    	String para = HttpClient.encodeParameters(params.toArray(new PostParameter[0]));
    	return api + "?" + para;
    }

}
