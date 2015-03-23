/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
names of its contributors may be used to endorse or promote products
derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package kx2_4j.oauth2;

import kx2_4j.KxConfig;
import kx2_4j.http.HttpClient;
import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;

public class KxOAuth {
	public static String CONSUMER_KEY = KxConfig.getValue("CONSUMER_KEY");//api key
    public static String CONSUMER_SECRET = KxConfig.getValue("CONSUMER_SECRET");//secret key
    public static String Redirect_uri = KxConfig.getValue("Redirect_uri");//需与注册信息中网站地址的域名一致，可修改域名映射在本地进行测试
    public static String scope = KxConfig.getValue("scope");//以空格分隔的权限列表，若为空字符串，代表请求默认的basic权限。
    
    public static String authorizationURL = KxConfig.getValue("authorizationURL");
    public static String accessTokenURL = KxConfig.getValue("accessTokenURL");
    public static String accessTokenURLssl = KxConfig.getValue("accessTokenURLssl");
    
    protected HttpClient http = new HttpClient();
    
    /**
     * a. Authorization Code：Web Server Flow，适用于所有有Server端配合的应用。
     * 第一步 获取Authorization Code
     * @param state 如果传递参数,会回传该参数.用于保持请求和回调的状态。在回调时,会在Query Parameter中回传该参数
     * @param display 登录和授权页面的展现样式，默认为“page”。还可以是"popup","iframe",
     * @return
     */
	public String getAuthorizeURLforCode(String state,String display)
	{
		return getAuthorizeURL("code",state,display);
	}
	
	/**
	 * a. Authorization Code：Web Server Flow，适用于所有有Server端配合的应用。
	 * 第二步  通过Authorization Code获取Access Token
	 * @param code 用于调用access_token接口获取授权后的access token
	 * @return
	 * @throws KxException
	 */
    public AccessToken getOAuthAccessTokenFromCode(String code) throws KxException {
    	AccessToken oauthToken = null;
        try {
            PostParameter[] params  = new PostParameter[5];
            params[0] = new PostParameter("grant_type", "authorization_code");
            params[1] = new PostParameter("client_id", CONSUMER_KEY);
            params[2] = new PostParameter("client_secret", CONSUMER_SECRET);
            params[3] = new PostParameter("code", code);
            params[4] = new PostParameter("redirect_uri", Redirect_uri);
            
            oauthToken = new AccessToken(http.get(accessTokenURL,params));
        } catch (KxException te) {
            throw new KxException("The user has not given access to the account.", te, te.getStatusCode());
        }
        return oauthToken;
    }
    
	/**
	 * b. Implicit Grant：User-Agent Flow，适用于所有无Server端配合的应用。
	 * 授权地址
	 * @param state 如果传递参数,会回传该参数.用于保持请求和回调的状态。在回调时,会在Query Parameter中回传该参数
     * @param display 登录和授权页面的展现样式，默认为“page”。还可以是"popup","iframe",
     * @return
	 */
	public String getAuthorizeURLforToken(String state,String display)
	{
		return getAuthorizeURL("token",state,display);
	}
	
    /**
     * c. Resource Owner Password Credentials：采用用户名、密码获取Access Token，适用于任何类型应用。
     * @param username
     * @param password
     * @return
     * @throws KxException
     */
    public AccessToken getOAuthAccessTokenFromPassword(String username,  String password) throws KxException {
       	AccessToken oauthToken = null;
        try {
            PostParameter[] params  = new PostParameter[6];
            params[0] = new PostParameter("grant_type", "password");
            params[1] = new PostParameter("client_id", CONSUMER_KEY);
            params[2] = new PostParameter("client_secret", CONSUMER_SECRET);
            params[3] = new PostParameter("username", username);
            params[4] = new PostParameter("password", password);
            params[5] = new PostParameter("scope", scope);
            oauthToken = new AccessToken(http.get(accessTokenURLssl, params));
        } catch (KxException te) {
            throw new KxException("The user has not given access to the account.", te, te.getStatusCode());
        }
        return oauthToken;
    }
    
    /**
     * d. Refresh Token：令牌刷新方式，适用于所有有Server端配合的应用。
     * @param refresh_token 用其它三种方式获取access_token时同时返回的refresh_token
     * @return
     * @throws KxException
     */
	public AccessToken getOAuthAccessTokenFromRefreshtoken(String refresh_token) throws KxException {
       	AccessToken oauthToken = null;
        try {
            PostParameter[] params  = new PostParameter[4];
            params[0] = new PostParameter("grant_type", "refresh_token");
            params[1] = new PostParameter("client_id", CONSUMER_KEY);
            params[2] = new PostParameter("client_secret", CONSUMER_SECRET);
            params[3] = new PostParameter("refresh_token", refresh_token);
            oauthToken = new AccessToken(http.get(accessTokenURL, params));
        } catch (KxException te) {
            throw new KxException("The user has not given access to the account.", te, te.getStatusCode());
        }
        return oauthToken;
    }
	
	protected String getAuthorizeURL(String type,String state,String display)
	{
		PostParameter[] params  = new PostParameter[6];
		params[0] = new PostParameter("response_type", type);
		params[1] = new PostParameter("client_id", CONSUMER_KEY);
		params[2] = new PostParameter("redirect_uri", Redirect_uri); 
		params[3] = new PostParameter("scope", scope);
		params[4] = new PostParameter("state", state);
		params[5] = new PostParameter("display", display);
		String query = HttpClient.encodeParameters(params);
		return authorizationURL + "?" + query;
	}


}
