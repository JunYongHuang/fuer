<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/calendar.js?v=1"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.js"></script>
<style type="text/css">
#roleBox, #spreadBox{
	margin: auto;
	width: 650px;
	min-height:160px;
	padding: 10px;
	border-radius: 0.5em 0.5em 0.5em 0.5em;
	position: absolute;
	top: 150px;
	background-color: #DDDDDD;
	display: none;
}
</style>
<title>用户列表-后台管理</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="userList.php">用户列表</a>
		</div>
		<div class="box_content">
			<div class="box_toolbar">
				<div class="toolbar_btns"></div>
				<form action="userList.php" method="post">
					<div class="toolbar_seacher">
						<div style="margin-right: 52px;">
							用户名： <input type="text" name="uid" value="${uid}"> 姓名： <input
								type="text" name="uname" value="${uname}"> 邮箱： <input
								type="text" name="uemail" value="${uemail}"> 类别：<select
								name="role">
								<option value="-1">------所有------</option>
								<c:forEach items="<%=CommonConstants.ROLE.all() %>" var="item">
									<option value="${item.key}"
										<c:if test='${role == item.key}'>selected="selected"</c:if>>${item.value}</option>
								</c:forEach>
							</select>
							来源：<select name="ufrom">
								<option value="">------所有------</option>
								<c:forEach items="<%=CommonConstants.USER_FROM.all()%>"
									var="item">
									<option value="${item.key}"
										<c:if test='${ufrom == item.key}'>selected="selected"</c:if>>${item.value}</option>
								</c:forEach>
							</select>
						</div>
						<div>
							注册日期从： <input type="text" name="regDateFrom"
								value="${regDateFrom}" onclick="fPopCalendar(event,this,this)"
								onfocus="this.select()"> 到<input type="text"
								name="regDateTo" value="${regDateTo}"
								onclick="fPopCalendar(event,this,this)" onfocus="this.select()">
							登陆日期从 <input type="text" name="loginDateFrom"
								value="${loginDateFrom}" onclick="fPopCalendar(event,this,this)"
								onfocus="this.select()"> 到 <input type="text"
								name="loginDateTo" value="${loginDateTo}"
								onclick="fPopCalendar(event,this,this)" onfocus="this.select()"> <input type="submit" value="提交">
						</div>
					</div>
				</form>
			</div>
			<%@ include file="/messages.jsp"%>
			<div style="margin: 35px 10px 5px 10px;">
				<span>说明:
					红色字体为黑名单用户(不能进入所有游戏服务器),绿色字体为测试人员(登陆后可看到并进入所有服务器),其它为普通用户(登陆后可看到正常服务器和维护服务器,看不到测试服务器,不可进入测试服务器,维护服务器及爆满的服务器)</span><span
					style="float: right;">共&nbsp;<b>${searchResult.fullListSize}</b>&nbsp;条&nbsp;&nbsp;</span>
			</div>
			<c:set var="roleTest" value="<%=CommonConstants.ROLE.ROLE_TEST%>" />
			<c:set var="roleBlack" value="<%=CommonConstants.ROLE.ROLE_BLACK%>" />
			<display:table name="searchResult" class="tbl_list"
				requestURI="userList.php" id="user" sort="external">
				<c:set var="color" value="" />
				<c:if test="${user.role == roleBlack}">
					<c:set var="color" value="#FF0000" />
				</c:if>
				<c:if test="${user.role == roleTest}">
					<c:set var="color" value="#00AA00" />
				</c:if>
				<display:column title="用户名" sortable="true" sortProperty="username">
					<font color="${color}">${user.username}</font> <br/>
					[<a href="#" onclick="showRoleBox('${user.username}',arguments[0]);">查看角色</a> 
					<c:if test="${!user.spread}">
						<span id="${user.id}" >| <a href="#" onclick="showSpreadBox('${user.id}','${user.username}','${user.name}',arguments[0]);">设为推广员</a></span>
					</c:if>
					]
				</display:column>

				<display:column title="姓名" sortable="true" sortProperty="name">
					<font color="${color}">${user.name}</font>
				</display:column>

				<display:column title="邮箱" sortable="true" sortProperty="email">
					<font color="${color}">${user.email}</font>
				</display:column>
				<display:column title="注册日期" sortable="true" sortProperty="regdate">
					<font color="${color}"> <fmt:formatDate
							value="${user.regDate}" pattern="yyyy-MM-dd HH:mm" /> </font>
				</display:column>
				<display:column title="登陆日期" sortable="true"
					sortProperty="lastLoginTime">
					<font color="${color}"> <fmt:formatDate
							value="${user.lastLoginDate}" pattern="yyyy-MM-dd HH:mm" /> </font>
				</display:column>
				
				<display:column title="来源" sortable="true" sortProperty="sourceFrom">
					<font color="${color}">${user.sourceFrom}</font>
				</display:column>
				<display:column title="测试人员" sortable="true" sortProperty="role">
					<input type="checkbox" onchange="changeRole(${user.id});"
						id="${user.id}"
						<c:if test="${user.role == roleTest}">checked="checked"</c:if>>
				</display:column>
				<display:column title="操作" sortable="false">
					<a href="userUpdate.php?id=${user.id}">修改</a>
				</display:column>
			</display:table>
		</div>
	</div>
	<div id="roleBox" >
		<div>用户 <span id="uname"></span> 经理人列表</div>
		<div style="margin: 10px;">
			<table class="tbl_list">
				<thead>
					<tr><th>服务器</th><th>经理名称</th><th>创建日期</th><th>最后登陆</th></tr>
				</thead>
				<tbody id="data">
				</tbody>
			</table>
		</div>
		<div><input type="button" value="关闭" onclick="hide('roleBox');"></div>
	</div>
	<div id="spreadBox" >
		<div>推广人员设置</div>
		<div style="margin: 10px;">
			<table class="tbl_list">
				<tr><th>用户名</th><td id="suname"></td></tr>
				<tr><th>推广人名称</th><td><input type="text" id="name" value=""/><input type="hidden" name="userId" id="userId" value=""></td></tr>
				<tr><th>链接地址</th><td>http://www.yyfuer.com/info/<input type="text" id="addressCode" value=""/>.php</td></tr>
				</tbody>
			</table>
		</div>
		<div><input type="button" value="保存" onclick="saveSpread();"><input type="button" value="取消" onclick="hide('spreadBox');"></div>
	</div>
	<script type='text/javascript'
		src='${ctx}/dwr/interface/AdminAjaxUtil.js'></script>
	<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
	<script type="text/javascript">
		function changeRole(userId){
			var role = get(userId).checked; 
			AdminAjaxUtil.changeUserRole(userId,role);
		}
		function showRoleBox(uname,e){
			var e = e||window.event; 
			var top = e.clientY - 160;
			var left = e.clientX + 50;
			get('roleBox').style.top= top + "px";
			get('roleBox').style.left= left + "px";
			
			AdminAjaxUtil.getUserRoles(uname, function(data) {
				$('#uname').html("<span>"+uname+"</span>");
				
				var dataStr = "";
				var serverMap = "${serverMap}";
				
				for(var i=0;i<data.length;i++){
					dataStr = dataStr + "<tr><td>"+data[i].serverName+"</td>";
					dataStr = dataStr + "<td>"+data[i].managerName+"</td>";
					dataStr = dataStr + "<td>"+data[i].creationDateStr+"</td>";
					dataStr = dataStr + "<td>"+data[i].loginDateStr+"</td></tr>";
				}
				if(dataStr != ""){
					$('#data').html(dataStr);
				}
				show('roleBox');
			});
		}

		function showSpreadBox(userId,uname,name, e){
			$('#suname').html("<span>"+uname+"</span>");
			$('#userId').val(userId);
			$('#addressCode').val(userId);
			$('#name').val(name);
			var e = e||window.event; 
			var top = e.clientY - 160;
			var left = e.clientX + 50;
			get('spreadBox').style.top= top + "px";
			get('spreadBox').style.left= left + "px";
			show('spreadBox');
		}
		
		function saveSpread(){
			var userId = get('userId').value;
			var addressCode = get('addressCode').value;
			var name = get('name').value;
			AdminAjaxUtil.saveSpreadUser(userId,name,addressCode, function(result){
				if(result == ''){
					alert("保存成功!");
					$("#"+userId).html("<span></span>");
					hide('spreadBox');
				}else{
					alert(result);
				}
			});
		}
	</script>
</body>
</html>