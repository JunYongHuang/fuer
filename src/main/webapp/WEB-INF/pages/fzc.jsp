<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>唯一一款3D足球网游--够足球为足球页游注入全新元素，创新玩法，全新游戏体验</title>
<meta name="keywords" content="足球游戏，屌丝游戏，足球网页游戏，足球网游，3d足球页游" />
<meta name="description"
	content="足球是一款全新的实时计算的足球网页游戏，自由的战术设置和真实的比赛场景使得玩家能够真正的在球场上运筹帷幄，一统球坛！" />
<style type="text/css">
<!--
body {
	background-color: #333333;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>

<body>
<c:if test="${empty sourcefrom}">
	<c:set var="sourcefrom" value=""></c:set>
</c:if>
	<div id="flashContent" align="center">
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" width="1000" height="562" id="FMain" align="middle">
				<param name="movie" value="images/login/fmain.swf?orz=100" />
				<param name="quality" value="high" />
				<param name="bgcolor" value="#333333" />
				<param name="play" value="true" />
				<param name="loop" value="true" />
				<param name="wmode" value="window" />
				<param name="scale" value="showall" />
				<param name="menu" value="true" />
				<param name="devicefont" value="false" />
				<param name="salign" value="" />
				<param name="allowScriptAccess" value="sameDomain" />
				<!--[if !IE]>-->
				<object type="application/x-shockwave-flash" data="images/login/fmain.swf?orz=100" width="1000" height="562">
					<param name="movie" value="images/login/fmain.swf?orz=100" />
					<param name="quality" value="high" />
					<param name="bgcolor" value="#333333" />
					<param name="play" value="true" />
					<param name="loop" value="true" />
					<param name="wmode" value="window" />
					<param name="scale" value="showall" />
					<param name="menu" value="true" />
					<param name="devicefont" value="false" />
					<param name="salign" value="" />
					<param name="allowScriptAccess" value="sameDomain" />
				<!--<![endif]-->
					<a href="http://www.adobe.com/go/getflash">
						<img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="获得 Adobe Flash Player" />
					</a>
				<!--[if !IE]>-->
				</object>
				<!--<![endif]-->
			</object>
		</div>
	
	<div style="display:none;">
		<form action="<c:url value="/j_security_check"/>" id="loginForm" method="post">
			用户名：<input name="j_username" type="text" id="j_username"/><br /> 
			密　码：<input name="j_password" type="password" id="j_password"/>
		</form>
	</div>
	<script type='text/javascript' src='${ctx}/dwr/interface/AjaxUtil.js'></script>
	<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
	<script type="text/javascript">
	function SaveName(username,password,email){
		if(!checkUsername(username)){
			alert('用户名长度为4-15个字符！');
		}else if(!checkPassword(password)){
			alert('密码长度为6-20个非空字符！');
		}else{
			var sourcefrom = '${sourcefrom}';
			AjaxUtil.sinaReg(username,password,password,email,sourcefrom, function(result) {
				if(result == ''){
					get("j_username").value=username;
					get("j_password").value=password;
					get('loginForm').submit();
				}else {
					alert(result);
				}
			});
		}
	}
	 function checkUsername(username){ 
		 var Regx = /^[A-Za-z0-9]{4,15}$/;
		 return Regx.test(username);
	 } 
	 function checkPassword(password){
		 var pattern=/^\S{6,20}$/gi; 
		 return pattern.test(password);
	 }
	 function get(id) {
		return document.getElementById(id);
	}
	</script>
	<div style='display:none;'>                 
         <script type="text/javascript">
                 var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
                 document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F0f11932282a8685e7a229f696fbe86bd' type='text/javascript'%3E%3C/script%3E"));
         </script>
    </div>
</body>
</html>
