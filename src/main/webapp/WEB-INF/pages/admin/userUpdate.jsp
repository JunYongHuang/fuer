<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>
<c:if test="${empty userForm.id}">
	新增用户
</c:if>
<c:if test="${not empty userForm.id}">
	更新用户信息
</c:if>
</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="userList.php">用户列表</a>
			&gt;&gt; <c:if test="${empty userForm.id}">新增用户</c:if>
			<c:if test="${not empty userForm.id}">更新用户</c:if>
		</div>
		<div class="box_content">
			<form:form method="post" modelAttribute="userForm" name="userForm">
				<%@ include file="/messages.jsp"%>
				<span class="err"><form:errors path="*" /> </span>

				<table width="100%" border="0" cellpadding="0" cellspacing="1"
					class="tbl_frm">
					<tbody>
					<c:if test="${empty userForm.id}">
						<tr>
							<th  width="15%">用户名：</th>
							<td>
							<form:input path="username" maxlength="18" class="ipt"/>
							</td>
						</tr>
						<tr>
							<th>用户类别：</th>
							<td>
								<form:select path="role">
									<form:options items="<%=CommonConstants.ROLE.all() %>"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<th>密码：</th>
							<td>
							<form:input path="password" maxlength="18" class="ipt"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty userForm.id}">
						<tr>
							<th width="15%">用户名：</th>
							<td>
								${userForm.username}
							</td>
						</tr>
						<tr>
							<th>用户类别：</th>
							<td>
								<form:select path="role">
									<form:options items="<%=CommonConstants.ROLE.all() %>"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<th>密码(新密码将发送至用户邮箱)：</th>
							<td>
								<input type="button" value="重置密码" onclick="resetUserPwd(${userForm.id});">&nbsp;&nbsp;&nbsp;&nbsp;<font color="" id="resetMsg"></font>
							</td>
						</tr>
						<script type='text/javascript' src='${ctx}/dwr/interface/AdminAjaxUtil.js'></script>
						<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
						<script type="text/javascript">
							function resetUserPwd(userId){
								var msg = get("resetMsg");
								AdminAjaxUtil.resetUserPwd(userId,function(result) {
									var msg = get("resetMsg");
									if(result == true){
										msg.color = "green";
										msg.innerHTML="密码重置成功,新密码已发送至用户邮箱";
									}else{
										msg.color = "red";
										msg.innerHTML="密码重置失败:用户没有填写邮箱";
									}
								});
							}
						</script>
						<tr>
							<th><label>姓名：</label>
							</th>
							<td>
							<form:input path="name" maxlength="5" class="ipt"/></td>
						</tr>
						<tr>
							<th><label>性别：</label>
							</th>
							<td>
								<form:radiobuttons path="sex" items="<%=CommonConstants.GENDER.all() %>"/>
							</td>
						</tr>
						<tr>
							<th>邮箱：</th>
							<td>
								<form:input path="email" maxlength="80" class="ipt"/>
							</td>
						</tr>
						<tr>
							<th>身份证号：</th>
							<td>
								<form:input path="idcard" maxlength="10" class="ipt"/>
							</td>
						</tr>
						<tr>
							<th>用户头像：</th>
							<td>
								<c:if test="${not empty userForm.pic}">
									<img src="${ctx}/images/avatar/${userForm.pic}" width="75"
										height="75">
								</c:if> 
								<c:if test="${empty userForm.pic }">
									<img src="${ctx}/images/user/no-head.gif" width="75" height="75">
								</c:if>
							</td>
						</tr>
						<tr>
							<th>最后访问日期：</th>
							<td>
								<fmt:formatDate	value="${userForm.lastLoginDate}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
						<tr>
							<th>注册日期：</th>
							<td>
								<fmt:formatDate	value="${userForm.regDate}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
					</c:if>
						<tr>
							<th>&nbsp;</th>
							<td>
							<form:hidden path="id"/>
							<input name="btn_save" type="submit" id="btn_save"
								value="  提交  ">
							</td>
						</tr>
					</tbody>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>