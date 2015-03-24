<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>推广人员-后台管理</title>
<script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="spreadList.php">推广信息</a>
		</div>
		<div class="box_content">
			<div class="box_toolbar">
				<div class="toolbar_btns">
<!-- 					<label> 批处理 <select id="selAction" name="type"> -->
<!-- 							<option value="">请选择...</option> -->
<!-- 							<option value="del">删除</option> -->
<!-- 							<option value="best">推荐</option> -->
<!-- 							<option value="not_best">取消推荐</option> -->
<!-- 							<option value="top">置顶</option> -->
<!-- 							<option value="not_top">取消置顶</option> -->
<!-- 							<option value="move_to">转移到分类</option> -->
<!-- 							<option value="suppliers_move_to">转移到供货商</option> -->
<!-- 					</select> </label> <label> <input type="submit" name="btn_batch" -->
<!-- 						id="btn_batch" value="确定" disabled="disabled"> </label> -->
				</div>
				<div class="toolbar_seacher">
					<form action="spreadList.php" method="post">
						统计日期从： <input type="text" name="dateFrom" value="${dateFrom}" onclick="fPopCalendar(event,this,this)" onfocus="this.select()" >
							&nbsp;到&nbsp;<input type="text" name=dateTo value="${dateTo}" onclick="fPopCalendar(event,this,this)" onfocus="this.select()">
						<input type="submit" value="提交">
					</form>
				</div>
			</div>
			<%@ include file="/messages.jsp"%>
			<div style="margin: 35px 10px 5px 10px;">
				<span>说明: 统计日期仅是指用户注册日期和订单生成日期,点击次数并未按日期进行统计.</span>
				<span style="float: right;">共&nbsp;<b>${searchResult.fullListSize}</b>&nbsp;条&nbsp;&nbsp;</span>
			</div>
			<display:table name="searchResult" class="tbl_list"
				requestURI="spreadList.php" id="spreaduser" sort="external">
				<display:column title="推广人" property="name" sortable="true"
					sortProperty="name"></display:column>
				<display:column title="点击次数" property="clickCount" sortable="true"
					sortProperty="clickCount"></display:column>
				<display:column title="推广用户" sortable="false">
					<c:if test="${spreaduser.userCount == 0}">--</c:if>
					<c:if test="${spreaduser.userCount > 0}">${spreaduser.userCount} 个</c:if>
				</display:column>
				<display:column title="充值总额" sortable="false">
					<c:if test="${spreaduser.chargeAmount == 0}">--</c:if>
					<c:if test="${spreaduser.chargeAmount > 0}">${spreaduser.chargeAmount/100.00} 元</c:if>
				</display:column>
				<display:column title="推广地址"sortable="true"
					sortProperty="addressCode">
					http://www.yyfuer.com/info/${spreaduser.addressCode}.php
				</display:column>
				<display:column title="加入日期" sortable="true" sortProperty="date">
					<fmt:formatDate value="${spreaduser.date}"
						pattern="yyyy-MM-dd HH:mm" />
				</display:column>
			</display:table>
		</div>
	</div>
</body>
</html>