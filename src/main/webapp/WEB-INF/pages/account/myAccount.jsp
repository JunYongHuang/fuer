<%@page import="java.util.Date"%>
<%@ include file="/taglibs.jsp"%>
<%@page import="com.cf.fuer.security.AccessHelper"%>
<%@page pageEncoding="utf-8"%>
<c:set var="loginUser" value="<%=AccessHelper.getLoginUser()%>" />

<!DOCTYPE html>
<html>
<head>
<title>我的账户</title>
</head>
<body>
	<div class="v_box">
		<div class="v_box_tit">会员中心</div>
		<div class="v_box_explain">

			<div class="v_left">
				<ul class="lmenu">
					<li><a href="${ctx}/account/myAccount.php">我的账户</a></li>
					<li><a href="${ctx}/account/edit.php">资料修改</a></li>
					<li><a href="${ctx}/account/changePassword.php">更改密码</a></li>
					<li><a href="${ctx}/account/chargeRecord.php">充值记录</a></li>
				</ul>
			</div>
			<div class="v_right">
				<div class="r_tit">我的账户</div>

				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="tbl_frm">
					<tbody>
						<tr>
							<td width="15%">
								<c:if test="${not empty loginUser.pic}">
									<img src="${ctx}/images/avatar/${loginUser.pic}" width="75"
										height="75">
								</c:if> 
								<c:if test="${empty loginUser.pic }">
									<img src="${ctx}/images/user/no-head.gif" width="75" height="75">
								</c:if><br> <a
								href="${ctx}/account/edit.php" class="blue">&lt;完善个人资料&gt;</a>
							</td>
							<td width="85%">你好 <span class="red">
									${loginUser.name } </span> ，欢迎访问。<br> <strong>账户余额 :</strong> <span
								class="red">0.00</span> <br> <strong>今天是 : </strong> <fmt:formatDate
									value="<%=new java.util.Date() %>" pattern="yyyy年MM月dd日" /> <br>
								<strong>你的邮箱：</strong> ${loginUser.email } <br></td>
						</tr>
					</tbody>
				</table>
			</div>


		</div>
	</div>
</body>
</html>