<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>
<c:if test="${empty serverForm.id}">
	新增服务器
</c:if>
<c:if test="${not empty serverForm.id}">
	更新服务器
</c:if>
</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="serverList.php">服务器列表</a>
			&gt;&gt; <c:if test="${empty serverForm.id}">新增服务器</c:if>
			<c:if test="${not empty serverForm.id}">
				更新服务器
			</c:if>
		</div>
		<div class="box_content">
			<form:form method="post" modelAttribute="serverForm" name="serverForm">
				<%@ include file="/messages.jsp"%>
				<span class="err"><form:errors path="*" /> </span>

				<table width="100%" border="0" cellpadding="0" cellspacing="1"
					class="tbl_frm">
					<tbody>
						<tr>
							<th width="10%">服务器代码：</th>
							<td>
							<c:if test="${empty serverForm.id}">
								<form:input path="code" maxlength="18" class="ipt"/>填写后不可更改!
							</c:if>
							<c:if test="${not empty serverForm.id}">
								<input value="${serverForm.code}" type="text" maxlength="18" class="ipt" disabled="disabled"/>不可更改!
								<form:hidden path="code"/>
							</c:if>
						</tr>

						<tr>
							<th>服务器名称：</th>
							<td>
							<form:input path="name" maxlength="18" class="ipt"/>
							</td>
						</tr>
						<tr>
							<th>服务器地址：</th>
							<td>
								<form:input path="ip" maxlength="50" class="ipt"/>
							</td>
						</tr>
						<tr>
							<th><label> 服务器端口：</label>
							</th>
							<td>
							<form:input path="port" maxlength="5" class="ipt"/></td>
						</tr>
						<c:if test="${not empty serverForm.id}">
							<tr>
								<th>当前用户数：</th>
								<td>
									${serverForm.currentCount}
									<c:set var="serverStatus" value="${serverForm.currentCount/serverForm.maxCount}" />
									<c:if test="${serverStatus < 0.9}">[<font color="green">空闲</font>]</c:if>
									<c:if test="${serverStatus >= 0.9 && serverStatus < 1}">[<font color="#DDDD00">拥挤</font>]</c:if>
									<c:if test="${serverStatus >= 1}">[<font color="red">爆满</font>]</c:if>
								</td>
							</tr>
						</c:if>
						<tr>
							<th>最大用户数：</th>
							<td>
								<form:input path="maxCount" maxlength="10" class="ipt"/>
							</td>
						</tr>
						<tr>
							<th>服务器类型：</th>
							<td>
								<form:select path="type">
									<form:options items="<%=CommonConstants.SERVER_TYPE.all()%>"/>
								</form:select>&nbsp;&nbsp;测试服务器和维护服务器只有测试人员才可进入,维护服务器普通用户可以看到,但不能进入
							</td>
						</tr>
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