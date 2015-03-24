<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>注册</title>

<meta name="titleImg" content="${ctx}/images/user/register.gif" />
</head>
<body>
	<div class="b_01">
		<img src="${ctx}/images/user/step.jpg">
	</div>
	<div class="b_02">
		已有账号 <a href="${ctx}/login.php">请直接登陆</a>
	</div>
	<div class="b_03">
		<form:form modelAttribute="userForm" method="post" name="userForm">

			<table cellspacing="0" cellpadding="0" border="0" width="800">
				<tbody>
					<tr>
						<th width="120"></th>
						<td colspan="3">
							<span class="err">
								<form:errors path="*" />
							</span>
						</td>
					</tr>
					<tr>
						<th width="120">用户名：</th>
						<td colspan="2">
							<form:input path="username" class="ipt"	maxlength="32" cssErrorClass="err" onblur="checkUsername();"/>&nbsp;&nbsp;
							<span class="note" id="usernameNote">（由字母a-z、数字0-9、下划线组成长度3-20,不区分大小写）</span>
						</td>
					</tr>
					<tr>
						<th width="120">设置密码：</th>
						<td colspan="2">
							<form:password path="password" class="ipt" onblur="checkPwd();"/>&nbsp;&nbsp;
							<span class="note"  id="passwordNote">（6-20个字符组成，区分大小写，不能和用户名 一样）</span>
						</td>
					</tr>
					<tr>
						<th width="120">确认密码：</th>
						<td colspan="2">
							<form:password path="confirmPassword" class="ipt" onblur="checkRePwd();"/>&nbsp;&nbsp;
							<span class="note" id="confirmPasswordNote">（请保持两次密码输入一致</span>
						</td>
					</tr>
					<tr>
						<th width="120">电子邮箱：</th>
						<td colspan="2">
							<form:input path="email" class="ipt" onblur="checkEmail();"/>&nbsp;&nbsp;
							<span class="note" id="emailNote">（您的电子邮箱也是将来找回密码和账户的重要认证）</span>
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<th width="120" height="20"><img -->
<%-- 							src="${ctx}/images/ico_right.jpg" width="36" height="34"></th> --%>
<%-- 						<td colspan="2"><img src="${ctx}/images/img_001.jpg" --%>
<!-- 							alt="网络游戏实名注册和防沉迷验" width="226" height="30"> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<th width="120" height="20">&nbsp;</th> -->
<!-- 						<td colspan="2">根据2010年8月1日实施的《网络游戏管理暂行办法》，网络游戏用户需使用有效身份证件进行实名注册。 -->
<!-- 							为保证流畅游戏体验，享受健康游戏生活，请广大玩家尽快实名注册。</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<th width="120" height="20">真实姓名：</th> -->
<!-- 						<td colspan="2"> -->
<%-- 							<form:input path="name" class="ipt" onblur="checkName();"/>&nbsp;&nbsp; --%>
<!-- 							<span class="note" id="nameNote"> 请填写2~4位汉字！</span> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<th width="120" height="30">身份证号：</th> -->
<!-- 						<td colspan="2"> -->
<%-- 							<form:input path="idcard" class="ipt" onblur="checkIdcard();"/>&nbsp;&nbsp; --%>
<!-- 							<span class="note" id="idcardNote"> （请填写15或18位正确身证号,为空时将受到防沉迷系统的限制）</span> -->
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<th width="120" height="30">验证码：</th>
						<td colspan="2">
							<input name="checkCode" type="text"	class="ipt" onblur="codeCheck();" id="code"/>&nbsp;&nbsp;
							<span class="note" id="codeCheckNote">（请填写下图中的字母或数字）</span>
						</td>
					</tr>
					<tr>
						<th width="120" height="40">&nbsp;</th>
						<td colspan="2"><img id="randomCodeImg"
							style="cursor: pointer;" onclick="javascript:changeRandomCode();"
							alt="看不清，换一张！" src="${ctx}/randomcode/register.ppg">
							&nbsp;&nbsp;看不清楚？ <a class="blue" onclick="changeRandomCode();"
							href="javascript:void(0);">换一张</a>
						</td>
					</tr>
					<tr>
						<th width="120" height="40">&nbsp;</th>
						<td width="20"><input name="checkbox" type="checkbox"
							id="checkbox" value="1" checked="checked">
						</td>
						<td width="660"><a href="${ctx}/shiming/agreement.htm" target="_blank">我授受够足球通行证用户服务协议</a></td>
					</tr>
					<tr>
						<th width="120" height="40">&nbsp;</th>
						<td colspan="2"><input type="submit" class="btn_reg"
							id="btn_save" name="btn_save" value=" "> <input name="do"
							type="hidden" id="do" value="reg">
						</td>
					</tr>
					<tr>
						<th width="120">&nbsp;</th>
						<td colspan="2">&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
<script type='text/javascript' src='${ctx}/dwr/interface/AjaxUtil.js'></script>
<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
	<script type="text/javascript">
		<%-- 用户名验证--%>
		function checkUsername() {
			var username = get("username").value;
			AjaxUtil.checkUsername(username, function(result) {
				setMsg("usernameNote",result);
			});
		}
		<%-- 密码验证--%>
		function checkPwd() {
			var username = get("username").value;
			var password = get("password").value;
			AjaxUtil.checkPwd(username,password, function(result) {
				setMsg("passwordNote",result);
			});
		}
		<%-- 确认密码验证--%>
		function checkRePwd() {
			var password = get("password").value;
			var confirmPassword = get("confirmPassword").value;
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
		<%-- 姓名验证--%>
		function checkName() {
			var name = get("name").value;
			AjaxUtil.checkName(name, function(result) {
				setMsg("nameNote",result);
			});
		}
		<%-- 身份证号验证--%>
		function checkIdcard() {
			var idcard = get("idcard").value;
			AjaxUtil.checkIdcard(idcard, function(result) {
				if(idcard != ''){
					setMsg("idcardNote",result);
				}
			});
		}
		<%-- 验证码 --%>
		function codeCheck() {
			var code = get("code").value;
			AjaxUtil.checkCode(code, function(result) {
				var msg = '';
				if(result == false){
					msg = "验证码输入不正确!";
				}
				setMsg("codeCheckNote",msg);
			});
		}
		function setMsg(id,msg){
			var note = get(id);
			if(msg == ''){
				note.className="ok";
				note.innerHTML = "正确";
			}else{
				note.className="err";
				note.innerHTML = msg;
			}
		}
		<%-- 验证码--%>
		function changeRandomCode() {
			var img = get("randomCodeImg");
			img.src = '${ctx}/' + 'randomcode/'
					+ Math.round(Math.random() * 10000) + ".ppg";
		}
	</script>
</body>
</html>