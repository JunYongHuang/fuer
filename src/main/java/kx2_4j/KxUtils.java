package kx2_4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

public final class KxUtils {
	private KxUtils(){
		
	}
	

	/** 
     * MD5 加密 
     */  
    public static String getMD5Str(String str) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
    }
    

	public static String BASE64Decode(String encode) throws IOException
	{
		encode = encode.replace('-', '+');
		encode = encode.replace('_', '/');
		if(encode.length() % 8 > 0){
			int n = 4 - encode.length() % 4;
			while(n-- > 0){
				encode += '=';
			}
		}
		String decode = new String(new BASE64Decoder().decodeBuffer(encode));
		return decode;
	}
	
	public static String HMAC_SHA256(String tocode, String key) throws NoSuchAlgorithmException, InvalidKeyException, IOException
	{
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(spec);
        return new String(mac.doFinal(tocode.getBytes()));
	}
}