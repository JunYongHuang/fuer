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
package kx2_4j.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AccessControlException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;

import kx2_4j.http.KxException;

/**
 * A utility class to handle HTTP request/response.
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class HttpClient implements java.io.Serializable {
	private static final long serialVersionUID = 808018030183407996L;
	
    private static final int OK = 200;// OK: Success!
    private static final int NOT_MODIFIED = 304;// Not Modified: There was no new data to return.
    private static final int BAD_REQUEST = 400;// Bad Request: The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.
    private static final int NOT_AUTHORIZED = 401;// Not Authorized: Authentication credentials were missing or incorrect.
    private static final int FORBIDDEN = 403;// Forbidden: The request is understood, but it has been refused.  An accompanying error message will explain why.
    private static final int NOT_FOUND = 404;// Not Found: The URI requested is invalid or the resource requested, such as a user, does not exists.
    private static final int NOT_ACCEPTABLE = 406;// Not Acceptable: Returned by the Search API when an invalid format is specified in the request.
    private static final int INTERNAL_SERVER_ERROR = 500;// Internal Server Error: Something is broken.  Please post to the group so the KxSDK team can investigate.
    private static final int BAD_GATEWAY = 502;// Bad Gateway: KxSDK is down or being upgraded.
    private static final int SERVICE_UNAVAILABLE = 503;// Service Unavailable: The KxSDK servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.

    private int retryCount = 3;
    private int retryIntervalMillis = 10000;
    private int connectionTimeout = 20000;
    private int readTimeout = 120000;
    
	private static final boolean DEBUG = true;
    private static boolean isJDK14orEarlier = false;
    private Map<String, String> requestHeaders = new HashMap<String, String>();

    static {
        try {
            String versionStr = System.getProperty("java.specification.version");
            if (null != versionStr) {
                isJDK14orEarlier = 1.5d > Double.parseDouble(versionStr);
            }
        } catch (AccessControlException ace) {
            isJDK14orEarlier = true;
        }
    }

    public HttpClient() {
        setRequestHeader("Accept-Encoding","gzip");
    }
    
    private org.apache.commons.httpclient.HttpClient getHttpClient(){
    	@SuppressWarnings("deprecation")
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory (), 443); 
    	Protocol.registerProtocol("https", myhttps); //忽略证书有效性检查
    	org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
    	return client;
    }
    
 	public Response multPartURL(String url,  PostParameter[] params) throws KxException{
  		PostMethod post = new PostMethod(url);
  		org.apache.commons.httpclient.HttpClient client = getHttpClient();
  		try {
    		long t = System.currentTimeMillis();
    		Part[] parts=null;
    		if(params==null){
    			parts=new Part[1];
    		}else{
    			parts=new Part[params.length];
    		}
    		if (params != null ) {
    			int i=0;
      			for (PostParameter entry : params) {
      				if(entry.getFile() != null)
      				{
      					File file = entry.getFile();
      		    		FilePart filePart=new FilePart(entry.getName(),file.getName(), file, entry.getContentType(),"UTF-8");//new MimetypesFileTypeMap().getContentType(file)
      		        	filePart.setTransferEncoding("binary");
      		        	parts[i++]= filePart;
      		    		/*图片上传失败时在这检查文件类型content-type;仅支持jpg,gif,png,bmp类型
      		    		for(int i=0;i<parts.length;i++)
      		    		{
      		    			parts[i].send(System.out);
      		    		}*/
      				}
      				else
      				{
      					parts[i++]=new StringPart(entry.getName(), entry.getValue(),"utf-8");
      				}
    			}
      		}

    		post.setRequestEntity( new MultipartRequestEntity(parts, post.getParams()) );
            client.executeMethod(post);

    		Response response=new Response();
    		response.setResponseAsString(post.getResponseBodyAsString());
    		response.setStatusCode(post.getStatusCode());

    		log("multPartURL URL:" + url + ", result:" + response + ", time:" + (System.currentTimeMillis() - t));
    		return response;
    	} catch (Exception ex) {
    		 throw new KxException(ex.getMessage(), ex, -1);
    	} finally {
    		post.releaseConnection();
    		client=null;
    	}
  	}

 	public Response get(String url, PostParameter[] params) throws KxException {
        if (url.indexOf("?") == -1) {
            url += "?";
        }
        if (null != params && params.length > 0) {
            url += HttpClient.encodeParameters(params);
        }
        log(url);
        return httpRequest(url, null, "GET");
    }
 	
    public Response post(String url, PostParameter[] postParameters) throws KxException {
        return httpRequest(url, postParameters, "POST");
    }

    public Response delete(String url) throws KxException {
    	return httpRequest(url, null, "DELETE");
    }
 
    public Response httpRequest(String url, PostParameter[] postParams,String httpMethod) throws KxException {
        int retriedCount;
        int retry = retryCount + 1;
        Response res = null;
        _FakeX509TrustManager.allowAllSSL();//忽略证书有效性检查
        for (retriedCount = 0; retriedCount < retry; retriedCount++) {
            int responseCode = -1;
            try {
                HttpURLConnection con = null;
                OutputStream osw = null;
                try {
                    con = getConnection(url);
                    con.setDoInput(true);
                    setHeaders(url, postParams, con, httpMethod);
                    if("GET".equals(httpMethod))
                    {
                        con.setRequestMethod("GET");
                        
                    }
                    else if ("POST".equals(httpMethod)) {
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
                        con.setDoOutput(true);
                        String postParam = "";
                        if (postParams != null) 
                        	postParam = encodeParameters(postParams);
                        log("Post Params: ", postParam);
                        byte[] bytes = postParam.getBytes("utf-8");
                        con.setRequestProperty("Content-Length", Integer.toString(bytes.length));
                        osw = con.getOutputStream();
                        osw.write(bytes);
                        osw.flush();
                        osw.close();
                    }
                    else if ("DELETE".equals(httpMethod)){
                        con.setRequestMethod("DELETE");
                    } 
                   
                   
                    res = new Response(con);
                    responseCode = con.getResponseCode();
                    if(DEBUG){
                        log("Response: ");
                        Map<String, List<String>> responseHeaders = con.getHeaderFields();
                        for (String key : responseHeaders.keySet()) {
                            List<String> values = responseHeaders.get(key);
                            for (String value : values) {
                                if(null != key){
                                    log(key + ": " + value);
                                }else{
                                    log(value);
                                }
                            }
                        }
                    }
                    if (responseCode != OK) {
                        if (responseCode < INTERNAL_SERVER_ERROR || retriedCount == retryCount) {
                        	if(responseCode == NOT_MODIFIED)
                        	{
                        		Response res1 = new Response();
                        		res1.setResponseAsString("{}");
                        		return res1;
                        	}
                        	else
                            throw new KxException(getCause(responseCode) + "\n" + res.asJSONObject().toString(), responseCode);
                        }
                        // will retry if the status code is INTERNAL_SERVER_ERROR
                    } else {
                        break;
                    }
                } finally {
                    try {
                        osw.close();
                    } catch (Exception ignore) {
                    }
                }
            } catch (IOException ioe) {
                // connection timeout or read timeout
                if (retriedCount == retryCount) {
                    throw new KxException(ioe.getMessage(), ioe, responseCode);
                }
            }
            try {
                if(DEBUG && null != res){
                    res.asString();
                }
                log("Sleeping " + retryIntervalMillis +" millisecs for next retry.");
                Thread.sleep(retryIntervalMillis);
            } catch (InterruptedException ignore) {
                //nothing to do
            }
        }
        return res;
    }

    public static String encodeParameters(PostParameter[] postParams) {
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < postParams.length; j++) {
            if (j != 0) {
                buf.append("&");
            }
            try {
                buf.append(URLEncoder.encode(postParams[j].name, "utf-8"))
                        .append("=").append(URLEncoder.encode(postParams[j].value, "utf-8"));
            } catch (java.io.UnsupportedEncodingException neverHappen) {
            }
        }
        return buf.toString();

    }

    /**
     * sets HTTP headers
     *
     * @param connection    HttpURLConnection
     * @param authenticated boolean
     */
    private void setHeaders(String url, PostParameter[] params, HttpURLConnection connection,String httpMethod) {
        log("Request: ");
        log(httpMethod + " ", url);

        for (String key : requestHeaders.keySet()) {
            connection.addRequestProperty(key, requestHeaders.get(key));
            log(key + ": " + requestHeaders.get(key));
        }
    }

    public void setRequestHeader(String name, String value) {
        requestHeaders.put(name, value);
    }

    public String getRequestHeader(String name) {
        return requestHeaders.get(name);
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        if (connectionTimeout > 0 && !isJDK14orEarlier) {
            con.setConnectTimeout(connectionTimeout);
        }
        if (readTimeout > 0 && !isJDK14orEarlier) {
            con.setReadTimeout(readTimeout);
        }
        return con;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + connectionTimeout;
		result = prime * result + readTimeout;
		result = prime * result
				+ ((requestHeaders == null) ? 0 : requestHeaders.hashCode());
		result = prime * result + retryCount;
		result = prime * result + retryIntervalMillis;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpClient other = (HttpClient) obj;
		if (connectionTimeout != other.connectionTimeout)
			return false;
		if (readTimeout != other.readTimeout)
			return false;
		if (requestHeaders == null) {
			if (other.requestHeaders != null)
				return false;
		} else if (!requestHeaders.equals(other.requestHeaders))
			return false;
		if (retryCount != other.retryCount)
			return false;
		if (retryIntervalMillis != other.retryIntervalMillis)
			return false;
		return true;
	}

	private static void log(String message) {
        if (DEBUG) {
            System.out.println("[" + new java.util.Date() + "]" + message);
        }
    }

    private static void log(String message, String message2) {
        if (DEBUG) {
            log(message + message2);
        }
    }

    private static String getCause(int statusCode){
        String cause = null;
        switch(statusCode){
            case NOT_MODIFIED:
                break;
            case BAD_REQUEST:
                cause = "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
                break;
            case NOT_AUTHORIZED:
                cause = "Authentication credentials were missing or incorrect.";
                break;
            case FORBIDDEN:
                cause = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
                break;
            case NOT_FOUND:
                cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
                break;
            case NOT_ACCEPTABLE:
                cause = "Returned by the Search API when an invalid format is specified in the request.";
                break;
            case INTERNAL_SERVER_ERROR:
                cause = "Something is broken.  Please post to the group so the Kx team can investigate.";
                break;
            case BAD_GATEWAY:
                cause = "Kx is down or being upgraded.";
                break;
            case SERVICE_UNAVAILABLE:
                cause = "Service Unavailable: The Kx servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
                break;
            default:
                cause = "";
        }
        return statusCode + ":" + cause;
    }
}
