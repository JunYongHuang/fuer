<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<meta name="titleImg" content="${ctx}/images/user/login.gif" />
<style type="text/css">
.thirdLoginIcon{
	width:80px;
	display: inline-block; 
	background-repeat:no-repeat;
	padding-left: 20px; 
	margin-top: 10px;
}
</style>
</head>
<body>
	<div class="b_02">
		还没有帐号 <a href="${ctx}/register.php">立即注册</a>
	</div>
	<div class="b_03">
		<div style="float: left;">
		<form action="<c:url value="/j_security_check"/>" name="loginForm"
			method="post" onsubmit="return checkCode();">
			<table cellspacing="0" cellpadding="0" border="0" width="500">
				<tbody>
					<tr>
						<th width="100"></th>
						<td>
						<%@ include file="/messages.jsp"%>
								<div class="err" id="error"><c:if test="${param.error == 'true'}">
									<%--         <fmt:message key="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}"></fmt:message> --%>
									用户名或密码错误
							</c:if> <c:if test="${not empty param.replaced }">
								您的账号在其它地方登录，您已被迫下线
							</c:if>
								</div></td>
					</tr>
					<tr>
						<th width="100" height="50">用户名/邮箱：</th>
						<td width="300"><input name="j_username" type="text"
							class="ipt" id="uid" maxlength="32">&nbsp;</td>
					</tr>
					<tr>
						<th height="50">密码：</th>
						<td><input name="j_password" type="password" class="ipt"
							id="pwd" maxlength="32">&nbsp;</td>
					</tr>
					<tr>
						<th height="50">输入下图字符：</th>
						<td><input name="code" type="text" class="ipt" id="code"
							maxlength="6"> &nbsp;<span class="note"></span></td>
					</tr>
					<tr>
						<th height="40">&nbsp;</th>
						<td><img style="cursor: pointer; border: none;"
							id="randomCodeImg" src="${ctx}/randomcode/first.ppg"
							onclick="changeRandomCode();" title="看不清，换一张">&nbsp;&nbsp;看不清楚？
							<a class="blue" onclick="changeRandomCode();"
							href="javascript:void(0);">换一张</a></td>
					</tr>
					<tr>
						<th height="50">&nbsp;</th>
						<td><input name="btn_save" type="submit" class="btn_login"
							id="btn_save" value=""><span style="margin-left: 30px;"><a href="${ctx}/forget.php" class="">忘记密码？</a></span></td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</form>
		</div>
		<div style="float: left;">
			<div style="font-size: 14px;margin-bottom: 10px;">使用合作网站登录</div>
			<div>
				<a href="${ctx}/login/qq.php" title="用QQ账号登陆" class="thirdLoginIcon" style="background-image:url('${ctx}/images/login/qq.png');">QQ账号</a>
				<a href="${ctx}/login/sina.php" title="用新浪微博账号登陆" class="thirdLoginIcon" style="background-image:url('${ctx}/images/login/sinaweibo.png');">新浪微博</a>
			</div>
			<div>
				<a href="${ctx}/login/renren.php" title="用人人网账号登陆" class="thirdLoginIcon" style="background-image:url('${ctx}/images/login/renren.png');">人人网</a>
				<a href="${ctx}/login/kaixin.php" title="开心网账号登陆" class="thirdLoginIcon" style="background-image:url('${ctx}/images/login/kaixin.png');">开心网</a>			
			</div>
		</div>
	</div>

<script type='text/javascript' src='${ctx}/dwr/interface/AjaxUtil.js'></script>
<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
	<script type="text/javascript">
		function changeRandomCode() {
			var img = get("randomCodeImg");
			img.src = '${ctx}/' + 'randomcode/'
					+ Math.round(Math.random() * 10000) + ".ppg";
		}
		function checkCode(){
			var code = get("code").value;
			AjaxUtil.checkCode(code,function(result) {
				if(result == true){
					document.loginForm.submit();
				}else{
					get("error").innerHTML = "验证码填写不正确!";
				}
			});
			return false;
		}
	</script>
</body>
</html>