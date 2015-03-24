<%@ include file="/taglibs.jsp"%>
<%@page import="com.cf.fuer.security.AccessHelper"%>
<%@page pageEncoding="utf-8"%>
<c:set var="loginUser" value="<%=AccessHelper.getLoginUser()%>" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>
修改密码
</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="changePassword.php">修改密码</a>
		</div>
		<div class="box_content">
			<form method="post" action="changePassword.php">
				<%@ include file="/messages.jsp"%>
					<table width="100%" border="0" cellpadding="0" cellspacing="1"	class="tbl_frm">
						<tbody>
							<tr>
								<th width="11%" align="right">旧密码：</th>
								<td width="89%"><input name="oldPwd" type="password"
									size="32" maxlength="64" class="ipt"></td>
							</tr>
							<tr>
								<th align="right">新密码：</th>
								<td><input name="newPwd" type="password" size="32"
									maxlength="64" class="ipt"></td>
							</tr>
							<tr>
								<th align="right" valign="middle">重复输入：</th>
								<td><label for="logo"></label> <input name="rePwd"
									type="password" size="32" maxlength="64" class="ipt"></td>
							</tr>
							<tr>
								<th>&nbsp;</th>
								<td><input name="btn_save" type="submit" id="btn_save"
									value="提交" class="btn_ok"></td>
							</tr>
						</tbody>
					</table>
			</form>
		</div>
	</div>
</body>
</html>