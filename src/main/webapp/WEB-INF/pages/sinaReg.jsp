<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>够屌丝 够足球 即刻逆袭</title>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${ctx}/images/favicon.ico"
	type="image/x-icon" />
<script type="text/javascript" src="${ctx}/scripts/global.js"></script>
<style type="text/css">
* {
	padding: 0px;
	margin: 0px;
	list-style: none;
}

body {
	background: url(images/sina/1-01.jpg) no-repeat;
	background-position: center;
	background-repeat: no-repeat;
}

.wrap {
	width: 1280px;
	min-height: 900px;
	margin: 0px auto;
	position: relative;
}

.wordImg {
	position: absolute;
	top: 290px;
	left: 230px;
}

#regBox {
	width: 493px;
	height: 218px;
	position: absolute;
	top: 400px;
	left: 597px;
	background: url(images/sina/reg.png) no-repeat;
	display: none;
}
input {
	position: absolute;
	width: 250px;
	height: 22px;
}

#ok {
	width: 100px;
	height: 31px;
	position: absolute;
	top: 168px;
	left: 290px;
	border: none;
	background: url(images/sina/ok.png) no-repeat;
	cursor: pointer;
}

#username {
	top: 46px;
	left: 135px;
}

#pwd {
	top: 74px;
	left: 135px;
}

#repwd {
	top: 102px;
	left: 135px;
}

#email {
	top: 130px;
	left: 135px;
}

#loginBox {
	position: absolute;
	top: 5px;
	left: 620px;
	font-family: "微软雅黑";
	font-size: 14px;
	font-weight: bold;
}

#loginBox input {
	height: 20px;
	width: 140px;
	margin-top: 4px;
	position: relative;
}

.logoBox {
	position: absolute;
	top: 17px;
	left: 115px;
}

.btnBox {
	position: absolute;
	top: 37px;
	left: 832px;
}
#thirdLogin{
	position: absolute;
	top: 64px;
	left: 620px;
	font-family: "微软雅黑";
	font-size: 12px;
	font-weight: bold;
}
a{
	text-decoration: none;
}
img {border-width: 0px 0px 0px 0px} 
#msg{
	position: absolute;
	top: 23px;
	left: 3px;
	color: #000000;
	width:490px;
	text-align: center;
	font-size: 11px;
}
</style>
</head>
<body>
	<div class="wrap">
	<img src="images/sina/blank.png" alt="" width="1280" height="900"  usemap="#mainmap"/>
<map name="mainmap" id="mainmap">
  <area shape="rect" coords="808,670,1031,741" href ="#" alt='开始游戏' onclick="show('regBox');"/>
</map>
		<div class="logoBox">
			<%-- <a href="http://www.yyfuer.com"> --%>
			<img src="images/sina/logo.png" alt="够足球"/>
			<%-- </a> --%>
		</div>
		<div id="loginBox">
		<form action="<c:url value="/j_security_check"/>" id="loginForm" method="post">
			用户名：<input name="j_username" type="text" id="j_username"/><br /> 
			密　码：<input name="j_password" type="password" id="j_password"/>
		</form>
		</div>
		<span id="thirdLogin">其它账号登陆:&nbsp;&nbsp; <a href="${ctx}/login/qq.php"
										title="用QQ账号登陆"><img src="${ctx}/images/login/qq.png"
											alt="用QQ账号登陆" /> </a> <a href="${ctx}/login/sina.php"
										title="用新浪微博账号登陆"><img
											src="${ctx}/images/login/sinaweibo.png" alt="用新浪微博账号登陆" /> </a>
										<a href="${ctx}/login/renren.php" title="用人人网账号账号登陆"><img
											src="${ctx}/images/login/renren.png" alt="用人人网账号登陆" /> </a> <a
										href="${ctx}/login/kaixin.php" title="用开心网账号账号登陆"><img
											src="${ctx}/images/login/kaixin.png" alt="用开心网账号登陆" /> </a> </span>
		<div class="btnBox">
			<img src="images/sina/btnBox.png" alt="够足球"  width="245" height="40"  usemap="#btnmap"/>
			<map name="btnmap" id="btnmap">
			  <area shape="rect" coords="0,0,80,25" href ="#" alt='登陆' onclick="login();"/>
			  <area shape="rect" coords="165,17,245,40" href ="#" alt='分享到新浪微博' onclick="share();"/>
			</map>
		</div>
		<img src="images/sina/diao.png?v=1" alt='你够"屌"吗？' class="wordImg" id="wordImg"/>
			<div id="regBox">
			<img src="images/sina/blank.png" width="493" height="218"  usemap="#regmap" style="border: 0px;"/>
			<map name="regmap" id="regmap">
  				<area shape="circle" coords="475,18,18" href ="#" alt="关闭" onclick="hide('regBox');"/>
			</map>
				<span id="msg"></span>
				<input type="text" id="username" value="" onblur="checkUsername();"/> 
				<input type="password" id="pwd" value="" onblur="checkPwd();"/> 
				<input type="password" id="repwd" value="" onblur="checkRePwd();"/>
				<input type="text" id="email" value="" onblur="checkEmail();"/> 
				<input type="button" id="ok" value="" onclick="submitForm();"/>
			</div>
	</div>
	<script type='text/javascript' src='${ctx}/dwr/interface/AjaxUtil.js'></script>
	<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
	<script type="text/javascript">
	function login(){
		var username = get("j_username").value;
		var password = get("j_password").value;
		AjaxUtil.sinaLogin(username,password,function(result) {
			if(result == ''){
				window.location.href="afterLogin.php";
			}else {
				get('loginForm').submit();
			}
		});
	}
	var t=0,z=3;
	function shake(){
		var i=t/180*Math.PI,x=Math.sin(i)*z+290,y=Math.cos(i)*z+230,s=wordImg.style;
		s.top=x+'px',s.left=y+'px';
		if(t>2160){
			t=0;
			setTimeout(shake,3000);
		}else{
			t = t + 90;
			setTimeout(shake,50);
		}
	}
	shake();
		function share(){
			window.open('http://service.weibo.com/share/share.php?title=够屌丝 够足球 即刻逆袭&url=http://www.yyfuer.com');
		}
		<%-- 用户名验证--%>
		function checkUsername() {
			var username = get("username").value;
			AjaxUtil.checkUsername(username, function(result) {
				setMsg("usernameNote",result);
			});
		}
		function submitForm(){
			var username = get("username").value;
			var password = get("pwd").value;
			var confirmPassword = get("repwd").value;
			var email = get("email").value;
			AjaxUtil.sinaReg(username,password,confirmPassword,email, function(result) {
				if(result == ''){
					window.location.href="afterLogin.php";
				}else{
					setMsg("submitErr",result);
				}
			});
		}
		<%-- 密码验证--%>
		function checkPwd() {
			var username = get("username").value;
			var password = get("pwd").value;
			AjaxUtil.checkPwd(username,password, function(result) {
				setMsg("passwordNote",result);
			});
		}
		<%-- 确认密码验证--%>
		function checkRePwd() {
			var password = get("pwd").value;
			var confirmPassword = get("repwd").value;
			var result = '';
			if(password != confirmPassword){
				result = "两次密码输入不一致";
			}
			setMsg("confirmPasswordNote",result);
		}
		<%-- Email验证--%>
		function checkEmail() {
			var email = get("email").value;
			AjaxUtil.checkEmail(email, function(result) {
				setMsg("emailNote",result);
			});
		}
		
		function setMsg(id,msg){
			var note = get('msg');
			if(msg == ''){
				note.innerHTML = "";
			}else{
				note.innerHTML = msg;
			}
		}
	</script>
	<div style="display: none;">
	<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F378542b8743c4469b9eee694286cac4a' type='text/javascript'%3E%3C/script%3E"));
</script>
	</div>
</body>
</html>
