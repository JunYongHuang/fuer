package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;

public class Page extends KxAPIcaller {

    public Page(String token)
    {
    	super(token);
    }
    
	/**
	 * 加粉丝
	 * @param pageid 机构主页UID，必填 
	 * @return 成功返回1
	 * @throws KxException
	 * @throws JSONException 
	 * @scope basic
	 */
	public Response addFan(long pageid) throws KxException, JSONException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("pageid", pageid));
	    return post("page/add_fan", params);
	}
}