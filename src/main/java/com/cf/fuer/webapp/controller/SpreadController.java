package com.cf.fuer.webapp.controller;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Decoder;

import com.cf.fuer.dao.IChargeDao;
import com.cf.fuer.manager.ISpreadManager;
import com.cf.fuer.util.SpreadUtils;
import com.cf.fuer.util.TokenUtil;

/**
 * Created by IntelliJ IDEA. User: kangmor Date: 13-7-4 Time: 下午2:31
 */
@Controller
public class SpreadController {
	@Autowired
	private ISpreadManager spreadManager;

	@Autowired
	private IChargeDao chargeDao;

	private final static String GOALGOAL = "GO";

	@RequestMapping("/tuiguang.php")
	public String spreadCount(HttpServletRequest request, HttpServletResponse response) {
		// SpreadUtils.addClick("3011", "20007");
		String ip = TokenUtil.getRequestIp(request);
		if (!spreadManager.ipExist(ip)) {
			String serverCode = "";
			String managerId = "";
			// 发放奖励
			String s = request.getParameter("p");
			if (s != null) {
				// Base64 decode the params
				String decodeString = null;
				if(s.startsWith("R0")){//Base64加密
					decodeString = base64Decoder(s);
				}else{
					decodeString = desDecoder(s);
				}
				if (decodeString != null) {
					// parse the param,eg. GG-0021-2321(platform name - server
					// code - manager id)
					String[] params = decodeString.split("-");
					if (params != null && params.length == 3) {
						if (GOALGOAL.equals(params[0])) {
							serverCode = params[1];
							managerId = params[2];
							managerId = managerId.substring(0, 5);
							// 活动时间内才发送奖励(使用chargeactivity表中servercode为-1的活动时间)
							if (chargeDao.hasActivity("-1")) {
								// 官网奖励
								SpreadUtils.addClick(serverCode, managerId);
							}
						}
					}

				}
				spreadManager.saveIP(ip, serverCode, managerId);
			}

		}
		return "redirect:/index.php";
	}

	protected String base64Decoder(String s) {
		BASE64Decoder decoder = new BASE64Decoder();
		String decodeString = null;
		try {
			byte[] b = decoder.decodeBuffer(s);
			decodeString = new String(b);
		} catch (Exception e) {
			return null;
		}
		return decodeString;
	}

	/**
	 * 用DES解密字符串
	 * 
	 * @param message
	 *            等解密的字符串
	 */
	public String desDecoder(String message) {
		try {
			byte[] messageByte = Hex.decodeHex(message.toCharArray());
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(getKey());

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(getKey());
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] retByte = cipher.doFinal(messageByte);
			return new String(retByte);
		} catch (Exception e) {
			return null;
		}
	}

	private static byte[] DES_KEY;

	/**
	 * DES解密用的key
	 */
	private byte[] getKey() throws UnsupportedEncodingException {
		if (DES_KEY == null) {
			DES_KEY = "2hzLyOTj".getBytes("UTF-8");
		}
		return DES_KEY;
	}

	/**
	 * 使用Des加密字符串
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private String desEncoder(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(getKey());

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(getKey());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		byte[] encryptByte = cipher.doFinal(message.getBytes("UTF-8"));
		return new String(Hex.encodeHex(encryptByte));
	}
}
