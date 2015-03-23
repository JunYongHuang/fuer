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

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;

/**
 * Representing authorized Access Token which is passed to the service provider in order to access protected resources.<br>
 * the token and token secret can be stored into some persistent stores such as file system or RDBMS for the further accesses.
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class AccessToken implements Serializable {
    private static final long serialVersionUID = -8344528374458826291L;
	private String access_token;
    private int expires_in;
    private String refresh_token;
    private String scope;
    private String state;


    String[] responseStr = null;

    public AccessToken(Response res) throws KxException {
        this(res.asString());
    }
    
    public void savePersis(HttpServletResponse response)
    {
    	saveToCookie(response);
    }
    
    public void saveToCookie(HttpServletResponse response)
    {
    	Cookie cookie = new Cookie("access_token",access_token);
		cookie.setMaxAge(this.expires_in);
		response.addCookie(cookie);
		cookie = new Cookie("refresh_token",refresh_token);
		cookie.setMaxAge(this.expires_in*2);
		response.addCookie(cookie);
    }
    
    public static String getFromPersis(HttpServletRequest request,HttpServletResponse response) throws KxException
    {
    	return getFromCookie(request, response);
    }
    
    public static String getFromCookie(HttpServletRequest request,HttpServletResponse response) throws KxException
    {
    	Cookie[] cookies = request.getCookies();
    	if(cookies == null)return null;
    	String access = null;
    	String refresh = null;
    	for(int i=0; i<cookies.length; i++)
    	{
    		if(cookies[i].getName().equals("access_token"))
    		{
    			access = cookies[i].getValue();
    		}
    		else if(cookies[i].getName().equals("refresh_token"))
    		{
    			refresh = cookies[i].getValue();
    		}
    	}
    	if(access != null)
    	{
    		return access;
    	}
    	else if(access == null && refresh != null)
    	{
			//过期重新获取
			System.out.println("refresh_token: "+refresh);
			KxOAuth connection = new KxOAuth();
			AccessToken accessToken=connection.getOAuthAccessTokenFromRefreshtoken(refresh);
			if(accessToken!=null)//
			{
				System.out.println(accessToken);
				accessToken.savePersis(response);
				return accessToken.getToken();
			}
			else
			{
				System.out.println("access token request fail");
			}
    	}
    	return null;
    }

    // for test unit
    AccessToken(String str) {
    	responseStr = str.split(",");
        access_token = getParameter("{\"access_token\"");
    	String sexpires_in = getParameter("\"expires_in\"");
    	expires_in = Integer.parseInt(sexpires_in);
        refresh_token = getParameter("\"refresh_token\"");
    	scope = getParameter("\"scope\"");
    	state = getParameter("\"state\"");
    }

    public AccessToken(String access_token,String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public AccessToken(String access_token,String refresh_token,String scope,String state,String expires) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.state = state;
        this.expires_in = Integer.parseInt(expires);
    }

    public String getToken() {
        return access_token;
    }

    public String getRefresh_Token() {
        return refresh_token;
    }


    public String getParameter(String parameter) {
    	String value = null;
        for (String str : responseStr) {
        	if (str.startsWith(parameter)) {
        		value = str.split(":")[1];
        		value = value.replace('"', (char) 0);
        		value = value.replace('}', (char) 0);
        		value = value.trim();
            	break;
            }
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessToken)) return false;

        AccessToken that = (AccessToken) o;
        if (!access_token.equals(that.access_token)) return false;
        if (!refresh_token.equals(that.refresh_token)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = access_token.hashCode();
        result = 31 * result + (int)(expires_in ^ (expires_in>>>32));
        result = 31 * result + refresh_token.hashCode();
        result = 31 * result + scope.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + Long.toString(expires_in) + '\'' +
                ", refresh_token='" + refresh_token + '\'' + 
                ", scope='" + scope + '\'' +           
                ", state='" + state + '\'' +           
                '}';
    }
}
