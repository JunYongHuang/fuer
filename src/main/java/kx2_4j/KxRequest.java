package kx2_4j;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class KxRequest {
	private String oauth_token;
	private long user_id;
	
	public KxRequest(String sign_request) throws IOException, NoSuchAlgorithmException, JSONException, InvalidKeyException
	{
		String req[] = sign_request.split("\\.",2);
		String sig = KxUtils.BASE64Decode(req[0]);
		JSONObject data = new JSONObject(KxUtils.BASE64Decode(req[1]));
		String algorithm = data.getString("algorithm");
		if(!algorithm.equals("HMAC-SHA256")){
			return;
		}
		String secret = KxConfigApp.getValue("KX_SKEY");
		String expected_sig = KxUtils.HMAC_SHA256(req[1],secret);
		if(sig.equals(expected_sig)){
			oauth_token = data.getString("oauth_token");
			user_id = data.getLong("user_id");
		}
		else{
			System.out.println("received:"+sig);
			System.out.println("computed:"+expected_sig);
		}
	}
	
	public String getToken()
	{
		return oauth_token;
	}
	
	public long getUID()
	{
		return user_id;
	}

}
