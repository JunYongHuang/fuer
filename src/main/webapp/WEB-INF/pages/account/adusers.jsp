<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
<title>用户列表</title>
</head>
<body>
	<div class="v_box">
		<div class="v_box_tit">用户列表</div>
		<div class="v_box_explain">

			<div class="v_left">
				<ul class="lmenu">
					<li><a href="${ctx}/account/myspread.php">我的推广</a></li>
					<li><a href="${ctx}/account/adusers.php">推广用户</a></li>
					<li><a href="${ctx}/account/adcharges.php">推广充值</a></li>
				</ul>
			</div>
			<div class="v_right">
				<div class="r_tit">
					<form action="adusers.php" method="post">
						注册日期从： <input type="text" name="dateFrom" value="${dateFrom}" onclick="fPopCalendar(event,this,this)" onfocus="this.select()" >
							&nbsp;到&nbsp;<input type="text" name=dateTo value="${dateTo}" onclick="fPopCalendar(event,this,this)" onfocus="this.select()"> <input
							type="submit" value="查询">
					<span style="float:right;">共&nbsp;<b>${searchResult.fullListSize}</b>&nbsp;条&nbsp;&nbsp;</span>
					</form>
				</div>
				<display:table name="searchResult" class="charge_list"
					requestURI="adusers.php" id="user" sort="external">
					<display:column title="用户名" sortable="true" sortProperty="username"
						property="username">
					</display:column>
					<display:column title="注册日期" sortable="true" sortProperty="regdate">
						<fmt:formatDate value="${user.regDate}" pattern="yyyy-MM-dd HH:mm" />
					</display:column>
				</display:table>
			</div>
		</div>
	</div>
</body>
</html>