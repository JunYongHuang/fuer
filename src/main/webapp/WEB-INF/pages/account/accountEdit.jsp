<%@page import="com.cf.fuer.CommonConstants"%>
<%@ include file="/taglibs.jsp"%>
<%@page import="com.cf.fuer.security.AccessHelper"%>
<%@page pageEncoding="utf-8"%>
<c:set var="loginUser" value="<%=AccessHelper.getLoginUser()%>" />

<!DOCTYPE html>
<html>
<head>
<title>资料修改</title>
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
				<div class="r_tit">资料更改</div>
				<form:form action="edit.php" method="post"
					modelAttribute="accountForm" name="accountForm"
					enctype="multipart/form-data">
					<span class="err"><form:errors path="*" /> </span>
					<span class="err"><b>为了您的密码安全，以便您能及时找回密码，请您完善您的邮箱信息。谢谢您的支持！祝您游戏愉快！</b></span>
					<%@ include file="/messages.jsp"%>
					<table width="600" border="0" cellpadding="0" cellspacing="10"
						class="tbl_frm">
						<tbody>
							<tr>
								<th width="149" align="right">账号：</th>
								<td width="451">${loginUser.username}</td>
							</tr>
							<tr>
								<th align="right">邮箱<span class="err">*</span>：</th>
								<td><input name="email" type="text" class="ipt" id="email"
									value="${loginUser.email}"></td>
							</tr>
						</tbody>
						<tbody>
							<tr>
								<th align="right">昵称<span class="err">*</span>：</th>
								<td><form:input path="name" class="ipt" value="${loginUser.name}"/>
							</tr>
							<tr>
								<th align="right">身份证号：</th>
								<td><form:input path="idcard" class="ipt" value="${loginUser.idcard}"/>
							</tr>
							<tr>
								<th align="right">头像：</th>
								<td><c:if test="${not empty accountForm.pic}">
										<img src="${ctx}/images/avatar/${accountForm.pic}" width="75"
											height="75">
									</c:if> <c:if test="${empty accountForm.pic }">
										<img src="${ctx}/images/user/no-head.gif" width="75"
											height="75">
									</c:if>

									<div style="height: 10px;"></div> <input type="file"
									name="picFile" id="picFile"><br></td>
							</tr>
							<tr>
								<th height="32" align="right">&nbsp;</th>
								<td>性别： 
								<form:radiobuttons path="sex" items="<%=com.cf.fuer.CommonConstants.GENDER.all() %>"/></td>
							</tr>
							<tr>
								<th align="right">&nbsp;</th>
								<td><input type="submit" name="btn_save" id="btn_save"
									value="   " class="btn_ok"> <input name="do"
									type="hidden" id="do" value="user_edit"></td>
							</tr>
						</tbody>
					</table>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>