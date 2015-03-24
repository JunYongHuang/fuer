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
					<li><a href="${ctx}/account/myAccount.php">我的账户</a>
					</li>
					<li><a href="${ctx}/account/edit.php">资料修改</a>
					</li>
					<li><a href="${ctx}/account/changePassword.php">更改密码</a>
					</li>
					<li><a href="${ctx}/account/chargeRecord.php">充值记录</a>
					</li>
				</ul>
			</div>
			<div class="v_right">
				<div class="r_tit">更改密码</div>

				<form method="post" action="changePassword.php">
					<%@ include file="/messages.jsp"%>
					<table width="100%" border="0" cellspacing="10" class="tbl_frm">
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
									value="    " class="btn_ok"> <input name="do"
									type="hidden" value="pass_edit"></td>
							</tr>
						</tbody>
					</table>

				</form>
			</div>


		</div>
	</div>
</body>
</html>