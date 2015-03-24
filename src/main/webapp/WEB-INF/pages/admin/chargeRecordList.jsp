<%@page import="com.cf.fuer.util.GameServerUtils"%>
<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>充值记录-后台管理</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="chargeRecordList.php">充值记录</a>
		</div>

		<c:set var="allStatusMap" value="<%=CommonConstants.CHARGE_STATUS.all() %>" />
		<c:set var="serverMap" value="<%=GameServerUtils.allGameServerMap() %>"></c:set>
		<div class="box_content">
			<div class="box_toolbar">
				<div class="toolbar_seacher">
					<form action="chargeRecordList.php" method="post">
						订单号： <input type="text" name="orderId" value="${orderId}"> 
						充值账号: <input type="text" name="payUser" value="${payUser}">
						经理名称: <input type="text" name="managerName" value="${managerName}">
						订单状态:
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
						<input type="submit" value="查询">
					</form>
				</div>
			</div>
			<div style="margin: 10px 10px;">
			<span style="float:right;">共&nbsp;<b>${searchResult.fullListSize}</b>&nbsp;条&nbsp;&nbsp;</span></div>
			<display:table name="searchResult" class="tbl_list"
				requestURI="chargeRecordList.php" id="charge" sort="external">
				<display:column title="订单号" sortable="true" property="orderId" sortProperty="orderId"
					href="chargeRecordDetail.php" paramId="id"></display:column>
				<display:column title="充值服务器" sortable="true"
					sortProperty="gameServer">${serverMap[charge.gameServer].name}</display:column>
				<display:column title="充值帐号" sortable="true" property="user" sortProperty="user"></display:column>
				<display:column title="经理名称" sortable="true" property="managerName" sortProperty="managerName"></display:column>
				<display:column title="充值金额" sortable="true" sortProperty="chargeAmount">${charge.chargeAmount/100.00} 元</display:column>
				<display:column title="金币" sortable="true" sortProperty="goldMoney">${charge.goldMoney} 金币</display:column>
				<display:column title="充值状态" sortable="true" sortProperty="status">${allStatusMap[charge.status]}</display:column>
				<display:column title="订单日期" sortable="true"
					sortProperty="createDate">
					<fmt:formatDate value="${charge.createDate}"
						pattern="yyyy-MM-dd HH:mm" />
				</display:column>
				<display:column>
					<a href="${ctx}/admin/sendCharge.php?orderId=${charge.orderId}">充值</a>
				</display:column>
			</display:table>
		</div>
	</div>
</body>
</html>