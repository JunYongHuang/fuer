package com.cf.fuer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import kx2_4j.http.KxException;
import kx2_4j.oauth2.KxOAuth;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

import com.renren.api.client.utils.ConfigUtil;
import com.renren.api.client.utils.HttpURLUtils;

public class ThirdLoginUtil {
	/**
	 * 获取新浪微博登陆的入口URL
	 * @return
	 */
	public static String getSinaAuthorizeUrl(){
		return WeiboConfig.getValue("authorizeURL").trim() + "?client_id="
		+ WeiboConfig.getValue("client_ID").trim() + "&redirect_uri="
		+ WeiboConfig.getValue("redirect_URI").trim()
		+ "&response_type=" + "code"
		+ "&scope="+WeiboConfig.getValue("scope").trim();
	}
	
	/**
	 * 获取新浪的登陆token
	 * @param code
	 * @return
	 * @throws WeiboException 
	 */
	public static AccessToken getSinaAccessToken(String code) throws WeiboException {
		return new weibo4j.Oauth().getAccessTokenByCode(code);
	}
	/**
	 * 获取开心网登陆的入口URL
	 * @return
	 */
	public static String getKaixnAuthorizeUrl(){
		return new KxOAuth().getAuthorizeURLforCode("goal", "");
	}
	
	/**
	 * 获取开心网的登陆token
	 * @param code
	 * @return
	 * @throws KxException 
	 */
	public static String getKaixinAccessToken(String code) throws KxException{
		kx2_4j.oauth2.AccessToken token = new KxOAuth().getOAuthAccessTokenFromCode(code);
		return token.getToken();
	}
	
	/**
	 * 获取人人网登陆的入口URL
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getRenrenAuthorizeUrl() throws UnsupportedEncodingException{
		String appId = ConfigUtil.getValue("renrenAppID");
		String renrenRedirectUri = ConfigUtil.getValue("renrenRedirectURI");
		String scope = ConfigUtil.getValue("renrenScope");
		String authorizeURL = ConfigUtil.getValue("renrenAuthorizeURL");
		
		String redirectUri = URLEncoder.encode(renrenRedirectUri, "UTF-8");
        //"https://graph.renren.com/oauth/authorize"连接的参数如下：
        StringBuilder params=new StringBuilder();
        params.append("?client_id=").append(appId);
        params.append("&").append("response_type=").append("code");
        params.append("&").append("redirect_uri=").append(redirectUri);
        params.append("&").append("display=").append("page");
        //操作的权限,根据具体需求自行选择
        params.append("&").append("scope=").append(scope);
        params.append("&").append("x_renew=").append("false"); //设置每次使用人人连接用户都需要登陆
        
        return authorizeURL + params.toString();
	}
	
	/**
	 * 获取人人网的登陆Token
	 * @param code
	 * @return
	 */	
	public static String getRenrenAccessToken(String code){
		String accessToken = "";
		//到人人网的OAuth 2.0的token endpoint用code换取access token
        String rrOAuthTokenEndpoint = ConfigUtil.getValue("renrenAccessTokenURL");
		String renrenRedirectUri = ConfigUtil.getValue("renrenRedirectURI");
        String appId = ConfigUtil.getValue("renrenAppID");
		String secretKey = ConfigUtil.getValue("renrenApiSecret");
		
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", appId);
        parameters.put("client_secret", secretKey);
        parameters.put("redirect_uri", renrenRedirectUri);//这个redirect_uri要和之前传给authorization endpoint的值一样
        parameters.put("grant_type", "authorization_code");
        parameters.put("code", code);

        String tokenResult = HttpURLUtils.doPost(rrOAuthTokenEndpoint, parameters);
        JSONObject tokenJson = (JSONObject) JSONValue.parse(tokenResult);
        if (tokenJson != null) {
            accessToken = (String) tokenJson.get("access_token");
        }
        return accessToken;
	}

}
