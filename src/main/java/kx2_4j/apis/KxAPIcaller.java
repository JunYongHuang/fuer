package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.KxConfig;
import kx2_4j.http.KxException;
import kx2_4j.http.HttpClient;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;

public class KxAPIcaller {

	public static String baseURL = KxConfig.getValue("baseURL");
    public static String format = KxConfig.getValue("format");//目前SDK只支持json
    protected HttpClient http = new HttpClient();
    protected String tokenname = null;
    protected String token = null;
    
    public KxAPIcaller(String token)
    {
    	setToken(token);
    }
    
    public void setToken(String token)
    {
    	this.tokenname = "access_token";
    	this.token = token;
    }
    
    //--------------base method----------

    protected Response get(String api, ArrayList<PostParameter> params) throws KxException {      
    	api = baseURL + api + "." + format;
    	params.add(new PostParameter(this.tokenname, this.token));
        return http.get(api,params.toArray(new PostParameter[0]));
    }
 
    protected Response post(String api, ArrayList<PostParameter> params) throws KxException {       
		api = baseURL + api + "." + format;
    	params.add(new PostParameter(this.tokenname, this.token));
        return http.post(api,params.toArray(new PostParameter[0]));
    }
 
    protected Response multPartURL(String api,  ArrayList<PostParameter> params) throws KxException {       
		api = baseURL + api + "." + format;
    	params.add(new PostParameter(this.tokenname, this.token));
        return http.multPartURL(api, params.toArray(new PostParameter[0]));
    }
}
