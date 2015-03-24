<%@page import="com.cf.fuer.util.GameServerUtils"%>
<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
<title>推广充值</title>
</head>
<body>
	<div class="v_box">
		<div class="v_box_tit">充值列表</div>
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
					<c:set var="serverMap" value="<%=GameServerUtils.allGameServerMap() %>"></c:set>
					<c:set var="allStatusMap" value="<%=CommonConstants.CHARGE_STATUS.all() %>" />
					<form action="adcharges.php" method="post">
						充值日期从： <input type="text" name="dateFrom" value="${dateFrom}" onclick="fPopCalendar(event,this,this)" onfocus="this.select()" >
							&nbsp;到&nbsp;<input type="text" name=dateTo value="${dateTo}" onclick="fPopCalendar(event,this,this)" onfocus="this.select()">
						状态:
						<select name="status">
							<option value="-1">-------不限-------</option>
							<c:forEach items="${allStatusMap}" var="item">
								<option value="${item.key}" <c:if test="${item.key == status}">selected='selected'</c:if>>${item.value}</option>
							</c:forEach>
						</select> 
						服务器:
						<select name="gameServer">
							<option value="">-------不限-------</option>
							<c:forEach items="${serverMap}" var="item">
								<option value="${item.value.code}" <c:if test="${item.value.code == gameServer}">selected='selected'</c:if>>${item.value.name}</option>
							</c:forEach>
						</select> 
							<input
							type="submit" value="查询">
					<span style="float:right;">共&nbsp;<b>${searchResult.fullListSize}</b>&nbsp;条&nbsp;&nbsp;</span>
					</form>
				</div>
				<display:table name="searchResult" class="charge_list"
					requestURI="adcharges.php" id="charge" sort="external">
					<display:column title="用户名" sortable="true" sortProperty="user"
						property="user">
					</display:column>
					<display:column title="服务器" sortable="true" sortProperty="gameServer">
						${serverMap[charge.gameServer].name}
					</display:column>
					<display:column title="经理名" sortable="true" sortProperty="managerName"
						property="managerName">
					</display:column>
					<display:column title="金额" sortable="true" sortProperty="chargeAmount">
						${charge.chargeAmount/100.00} 元
					</display:column>
					<display:column title="充值状态" sortable="true" sortProperty="status">${allStatusMap[charge.status]}</display:column>
					<display:column title="订单日期" sortable="true" sortProperty="createDate">
						<fmt:formatDate value="${charge.createDate}" pattern="yyyy-MM-dd HH:mm" />
					</display:column>
				</display:table>
			</div>
		</div>
	</div>
</body>
</html>