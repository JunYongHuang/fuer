<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>服务器列表-后台管理</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="serverList.php">服务器列表</a>
		</div>
		<div class="box_content">
			<div class="box_toolbar">
				<div class="toolbar_btns">
				</div>
				<div class="toolbar_seacher">
				</div>
			</div>
			<div style="margin: 10px 10px;">说明:
				测试服务器和维护服务器只有测试人员才可进入,维护服务器普通用户可以看到,但不能进入</div>
			<c:set var="serverTypeMap" value="<%=CommonConstants.SERVER_TYPE.all() %>" />
			<display:table name="searchResult" class="tbl_list"
				requestURI="serverList.php" id="gameServer" sort="external">
				<display:column title="服务器名称" property="name" sortable="true"
					sortProperty="name" href="gameServer.php" paramId="id"
					paramProperty="id"></display:column>
				<display:column title="服务器类型" sortable="true" sortProperty="type">
					${serverTypeMap[gameServer.type]}
				</display:column>
				<display:column title="最大用户数" property="maxCount" sortable="true"
					sortProperty="maxCount"></display:column>
				<display:column title="当前用户数" property="currentCount" sortable="true"
					sortProperty="currentCount"></display:column>
				<display:column title="服务器状态" sortable="true" sortProperty="currentCount">
					<c:set var="serverStatus" value="${gameServer.currentCount/gameServer.maxCount}" />
					<c:if test="${serverStatus < 0.9}"><font color="green">空闲</font></c:if>
					<c:if test="${serverStatus >= 0.9 && serverStatus < 1}"><font color="#DDDD00">火爆</font></c:if>
					<c:if test="${serverStatus >= 1}"><font color="red">爆满</font></c:if>
				</display:column>
				<display:column title="服务器编号" property="code" sortable="true"
					sortProperty="code"></display:column>
				<display:column title="服务器地址" property="ip" sortable="true"
					sortProperty="ip"></display:column>
				<display:column title="服务器端口" property="port" sortable="true"
					sortProperty="port"></display:column>
			</display:table>
		</div>
	</div>
</body>
</html>