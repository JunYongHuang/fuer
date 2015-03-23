package com.cf.fuer.webapp.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kx2_4j.http.KxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import weibo4j.Users;
import weibo4j.model.WeiboException;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;
import com.cf.fuer.util.ThirdLoginUtil;
import com.cf.fuer.util.TokenUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.renren.api.client.RenrenApiClient;

/**
 * 忘记密码
 */
@Controller
public class ThirdLoginController {
	
	private Log log = LogFactory.getLog(ThirdLoginController.class);
	
	@Autowired
	private IUserManager userManager;
	
	private static final String targetUrl = "/afterLogin.php";
	
	/**
	 * QQ登陆
	 */
	@RequestMapping("/login/qq.php")
	public String qqLogin(HttpServletRequest request) {
		String url = "/";
		try {
			url = new Oauth().getAuthorizeURL(request);
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
		return "redirect:"+url;
	}

	
	@RequestMapping("/login/afterqq.php")
	public String afterQQLogin(HttpServletRequest request,HttpServletResponse response) {
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

            String accessToken   = null;
            String openID        = null;

            if (accessTokenObj.getAccessToken().equals("")) {
//                我们的网站被CSRF攻击了或者用户取消了授权
//                做一些数据统计工作
                ControllerUtil.setErrorMessage(request, "没有获取到响应参数");
                return "redirect:/login.php";
            } else {
                accessToken = accessTokenObj.getAccessToken();
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                
                String username = "qq_"+openID;
                try{//用户已存在,登陆
                    User user = userManager.loadUserByUsername(username);
                    AccessHelper.login(user);        
                    TokenUtil.saveLoginUserToken(request);
                }catch(Exception e){//用户不存在,创建用户                    
                    UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                    UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                    if (userInfoBean.getRet() == 0) {
                    	String name = userInfoBean.getNickname();
                    	String gender = userInfoBean.getGender();
                    	int sex = CommonConstants.GENDER.MALE;
                    	if(gender.equalsIgnoreCase("女")){
                    		sex = CommonConstants.GENDER.FEMALE;
                    	}
            			String sourcefrom = getCookie(request,"sourcefrom");
                    	Long userId = userManager.createThirdUser(username, sex, CommonConstants.LOGIN_TYPE.QQ, openID, name,sourcefrom);
            			log.info("用户["+username+"]注册来源："+getCookie(request,"referer"));
                        AccessHelper.login(userManager.get(userId));        
                        TokenUtil.saveLoginUserToken(request); 
                    } else {
                    	ControllerUtil.setErrorMessage(request, "很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
                        return "redirect:/login.php";
                    }
                }
            }
        } catch (QQConnectException e) {
        	log.error("QQ登陆失败,原因:"+e.getMessage());
        }
        return "redirect:"+targetUrl;
	}
	
	/**
	 * 新浪微博登陆
	 */
	@RequestMapping("/login/sina.php")
	public String sinaLogin(HttpServletRequest request) {
		return "redirect:"+ThirdLoginUtil.getSinaAuthorizeUrl();
	}
	/**
	 * 新浪微博登陆完成
	 */
	@RequestMapping("/login/aftersina.php")
	public String afterSinaLogin(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
        if (code == null || code.length() == 0) {
            //缺乏有效参数，跳转到登录页去
            ControllerUtil.setErrorMessage(request, "没有获取到响应参数");
            return "redirect:/login.php";
        }
        //到新浪微博的OAuth 2.0的token endpoint用code换取access token
		try {
			weibo4j.http.AccessToken accessToken = ThirdLoginUtil.getSinaAccessToken(code);
			String uid = accessToken.getUid();
			String username = "sw_"+uid;
			try{//用户已存在,登陆
                User user = userManager.loadUserByUsername(username);
                AccessHelper.login(user);        
                TokenUtil.saveLoginUserToken(request);
            }catch(Exception e){//用户不存在,创建用户             
    			String token = accessToken.getAccessToken();
    			Users um = new Users();
                um.client.setToken(token);
                weibo4j.model.User user = um.showUserById(uid);
                
                String name = user.getName();
                int sex = CommonConstants.GENDER.MALE;
            	if(user.getGender().equalsIgnoreCase("f")){
            		sex = CommonConstants.GENDER.FEMALE;
            	}
    			String sourcefrom = getCookie(request,"sourcefrom");
                Long userId = userManager.createThirdUser(username, sex, CommonConstants.LOGIN_TYPE.SINA_WEBO, String.valueOf(uid), name,sourcefrom);
    			log.info("用户["+username+"]注册来源："+getCookie(request,"referer"));
                AccessHelper.login(userManager.get(userId));        
                TokenUtil.saveLoginUserToken(request);
            }
		} catch (WeiboException e) {
	        e.printStackTrace();
			ControllerUtil.setErrorMessage(request, "很抱歉，我们没能正确获取到您的信息. " );
            return "redirect:/login.php";
		}
        return "redirect:" + targetUrl;
	}
	/**
	 * 开心网登陆
	 */
	@RequestMapping("/login/kaixin.php")
	public String kaixinLogin(HttpServletRequest request) {
		return "redirect:"+ThirdLoginUtil.getKaixnAuthorizeUrl();
	}
	/**
	 * 开心网登陆完成
	 */
	@RequestMapping("/login/afterkaixin.php")
	public String afterKaixinLogin(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
        if (code == null || code.length() == 0) {
            //缺乏有效参数，跳转到登录页去
            ControllerUtil.setErrorMessage(request, "没有获取到响应参数");
            return "redirect:/login.php";
        }
        //到开心网的OAuth 2.0的token endpoint用code换取access token
		try {
			String token = ThirdLoginUtil.getKaixinAccessToken(code);
			kx2_4j.apis.Users users = new kx2_4j.apis.Users(token);
			kx2_4j.model.users.User kx_user = new kx2_4j.model.users.User(users.getCurUser(""));
			long uid = kx_user.getUid();
			String username = "kx_"+uid;
			try{//用户已存在,登陆
                User user = userManager.loadUserByUsername(username);
                AccessHelper.login(user);        
                TokenUtil.saveLoginUserToken(request);
            }catch(Exception e){//用户不存在,创建用户             
            	
                String name = kx_user.getName();
                int gender = kx_user.getGender();
                int sex = CommonConstants.GENDER.MALE;
            	if(gender == 1){
            		sex = CommonConstants.GENDER.FEMALE;
            	}

    			String sourcefrom = getCookie(request,"sourcefrom");
                Long userId = userManager.createThirdUser(username, sex, CommonConstants.LOGIN_TYPE.KAIXIN, String.valueOf(uid), name,sourcefrom);
    			log.info("用户["+username+"]注册来源："+getCookie(request,"referer"));
                AccessHelper.login(userManager.get(userId));        
                TokenUtil.saveLoginUserToken(request);
            }
		} catch (KxException e) {
	        e.printStackTrace();
			ControllerUtil.setErrorMessage(request, "很抱歉，我们没能正确获取到您的信息. " );
            return "redirect:/login.php";
		}
        return "redirect:" + targetUrl;
	}
	
	/**
	 * 人人网登陆
	 */
	@RequestMapping("/login/renren.php")
	public String renrenLogin(HttpServletRequest request) {
		String url = "/";
		try {
			url = ThirdLoginUtil.getRenrenAuthorizeUrl();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:"+url;
	}
	
	/**
	 * 人人网登陆完成
	 */
	@RequestMapping("/login/afterrenren.php")
	public String afterRenrenLogin(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
        if (code == null || code.length() == 0) {
            //缺乏有效参数，跳转到登录页去
            ControllerUtil.setErrorMessage(request, "没有获取到响应参数");
            return "redirect:/login.php";
        }
        //到人人网的OAuth 2.0的token endpoint用code换取access token
        String accessToken = ThirdLoginUtil.getRenrenAccessToken(code);
        if (!accessToken.isEmpty()) {
            //调用SDK获得用户信息
            RenrenApiClient apiClient = RenrenApiClient.getInstance();
            com.renren.api.client.param.impl.AccessToken token = new com.renren.api.client.param.impl.AccessToken(accessToken);
            int rrUid = apiClient.getUserService().getLoggedInUser(token);
            String username = "rr_"+rrUid;
            try{//用户已存在,登陆
                User user = userManager.loadUserByUsername(username);
                AccessHelper.login(user);        
                TokenUtil.saveLoginUserToken(request);
            }catch(Exception e){//用户不存在,创建用户                    
                JSONArray userInfo = apiClient.getUserService().getInfo(String.valueOf(rrUid),
                    "name,sex", token);
                if (userInfo != null && userInfo.size() > 0) {
                    JSONObject currentUser = (JSONObject) userInfo.get(0);
                    if (currentUser != null) {
                        String name = (String) currentUser.get("name");
                        int sex = Integer.valueOf(currentUser.get("sex").toString());
            			String sourcefrom = getCookie(request,"sourcefrom");
                    	Long userId = userManager.createThirdUser(username, sex, CommonConstants.LOGIN_TYPE.RENREN, String.valueOf(rrUid), name,sourcefrom);
            			log.info("用户["+username+"]注册来源："+getCookie(request,"referer"));
                        AccessHelper.login(userManager.get(userId));        
                        TokenUtil.saveLoginUserToken(request);
                    }
                }else{
                	ControllerUtil.setErrorMessage(request, "很抱歉，我们没能正确获取到您的信息. " );
                    return "redirect:/login.php";
                }
            }
        }
		return "redirect:"+targetUrl;
	}
	
	/**
	 * 从cookie中获取注册用户的来源，如果cookie不存在，返回0
	 * @return
	 */
	private String getCookie(HttpServletRequest request,String cookieName){
		Cookie cookies[] = request.getCookies() ;
        Cookie c1 = null ;
        if(cookies != null){
            for(int i=0;i<cookies.length;i++){
               c1 = cookies[i] ;
               if(c1.getName().equalsIgnoreCase(cookieName)){
            	   return c1.getValue();
               }
            }
        }
        return "";
	}
}
