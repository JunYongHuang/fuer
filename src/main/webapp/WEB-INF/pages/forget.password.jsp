<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>忘记密码</title>
<meta name="titleImg" content="${ctx}/images/user/login.gif" />
</head>
<body>
	<div class="b_02">
		<a href="${ctx}/index.php">返回首页</a>
	</div>
	<div class="b_03">
		<form:form method="post">
			<c:if test="${not empty error}">
				<span id="error" class="error">${error}</span>
			</c:if>
			<c:if test="${not empty successMessage}">
				<script type="text/javascript" src="${ctx}/scripts/global.js"></script>
				<script type="text/javascript">
					function intervalFun(button, oldVal, newVal) {
						if (newVal > 0) {
							button.value = newVal;
							newVal = newVal - 1;
							setTimeout(function() {
								intervalFun(button, oldVal, newVal);
							}, 1000);
						} else {
							button.disabled = '';
							button.value = oldVal;
						}
					}
					addOnload(function() {
						var button = get("send");
						var buttonLabel = button.value;
						button.disabled = 'disabled';
						intervalFun(button, buttonLabel, 10);
					});
				</script>
				<span id="successMessage">${successMessage}</span>
				<c:remove var="successMessage" scope="session" />
			</c:if>
			<div class="MbuttDiv" align="center">
				<div>Email：<input type="text" name="email" class="ipt" /></div>
				<br>
				<div><input type="submit" value="发送" name="send" id="send" class="itemBg"></div>
				<br>(请输入注册时的Email,新密码将以邮件方式通知您)
			</div>
		</form:form>
	</div>
</body>
</html>
