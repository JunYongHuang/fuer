<%@page import="com.cf.fuer.security.AccessHelper"%>
<%@page import="com.cf.fuer.util.GameServerUtils"%>
<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>服务器列表</title>
</head>
<body>
<c:set var="loginUser" value="<%=AccessHelper.getLoginUser()%>" />
	<div class="nav_box">
		<div class="fl">${articleType.parent.label}</div>
		<div class="fr">
		<span>当前位置 > </span> <a href="${ctx}/">首页</a> > <a href="${ctx}/allServers.php">服务器列表</a>
		</div>
	</div>
	<div class="content_box t3">
		<%@ include file="/messages.jsp"%>
			<div class="n_tit">选择服务器<br/>
				<div style="color: #595959;font-size: 14px;">(服务器维护时间为每天04:00 - 07:00)</div>
			</div>
			<c:set var="allServerMap" value="<%=GameServerUtils.allGameServerMap() %>" />
			<div style=" background-color: #E8E8E8;border: 1px solid #C4C4C4;">
			<div style="background-color: #F5F5F5;
    border: 1px solid #D8D8D8;
    height: 530px;
    margin: 3px;
    padding: 18px 28px;">
			<%--登陆用户--%>
			<c:if test="${not empty loginUser}">
				<%--黑名单用户--%>
				<c:if test="${loginUser.black}">
					你已被管理员限制进入游戏,请联系管理员解除限制!
				</c:if> 
				<%--正常用户--%>
				<c:if test="${!loginUser.black}">
					<c:set var="regServers"	value="<%=GameServerUtils.getUserRegServers() %>" />
					<c:if test="${not empty regServers}">
						<div class=serverList>
							<div class=serverTitle>最近登陆</div>
							<div class="serverContent">
								<c:forEach items="${regServers}" var="serverCode">
									<span class="serverItem"> 
										<%--测试人员和管理员 --%>
										<c:if test="${loginUser.test || loginUser.admin}">
											<c:if test="${allServerMap[serverCode].maintain}">
												<a href="${ctx}/games/fm.php?c=${serverCode}" target="_blank" class="itemBg">
													${allServerMap[serverCode].name}<c:if test="${allServerMap[serverCode].maintain}">[<font color="#999999">维护</font>]</c:if>
												</a>
											</c:if>
											<c:if test="${!allServerMap[serverCode].maintain}">
												<a href="${ctx}/games/fm.php?c=${serverCode}" target="_blank" class="itemBg">
													${allServerMap[serverCode].name}
												</a>
											</c:if>
										</c:if>
										<%--普通人员 并且不是测试服务器,可以看到--%>	
										<c:if test="${loginUser.common && !allServerMap[serverCode].test}">
											<c:if test="${!allServerMap[serverCode].maintain}">
												<a href="${ctx}/games/fm.php?c=${serverCode}"	target="_blank" class="itemBg">${allServerMap[serverCode].name}</a>
											</c:if> 
											<c:if test="${allServerMap[serverCode].maintain}">
													<span class="itemBg">${allServerMap[serverCode].name}[<font	color="#999999">维护</font>]</span>
											</c:if>
										</c:if>
									</span>
								</c:forEach>
							</div>
						</div>
					</c:if>
					<div class="border"></div>
					<div class=serverList>
						<div class=serverTitle>服务器列表</div>
						<div class=serverContent>
							<c:forEach items="${allServerMap}" var="item">
							<c:set var="serverStatus" value="${item.value.currentCount/item.value.maxCount}" />
							<%--测试人员和管理员 --%>
							<c:if test="${loginUser.test || loginUser.admin}">
								<span class="serverItem"> 
									<a href="${ctx}/games/fm.php?c=${item.key}" target="_blank" class="itemBg">${item.value.name}
										<c:if test="${serverStatus < 0.9}">[<font color="#57f382">空闲</font>]</c:if>
										<c:if test="${serverStatus >= 0.9 && serverStatus < 1}">[<font color="yellow">火爆</font>]</c:if>
										<c:if test="${serverStatus >= 1}">[<font color="#f43936">爆满</font>]</c:if>
									</a>
								</span>
							</c:if>
							<%--普通用户 --%>
							<c:if test="${loginUser.common && !item.value.test}">
								<span class="serverItem"> 
									<c:if test="${!item.value.maintain}">
										<c:if test="${serverStatus >= 1}">
											<span class="itemBg">${item.value.name}[<font color="#f43936">爆满</font>]</span>
										</c:if>
										<c:if test="${serverStatus < 1}">
											<a href="${ctx}/games/fm.php?c=${item.key}" target="_blank" class="itemBg">${item.value.name}
												<c:if test="${serverStatus < 0.9}">[<font color="#57f382">空闲</font>]</c:if>
												<c:if test="${serverStatus >= 0.9}">[<font color="yellow">火爆</font>]</c:if>
											</a>
										</c:if>
									</c:if> 
									<c:if test="${item.value.maintain}">
										<span class="itemBg">${item.value.name}[<font color="#999999">待开</font>]</span>
									</c:if>
								</span>
							</c:if>
							</c:forEach>
						</div>
					</div>
			</c:if>
			</c:if>
			<%--未登陆用户--%>
			<c:if test="${empty loginUser}">
				<div class=serverList>
					<div class=serverTitle>服务器列表</div>
					<div class=serverContent>
						<c:forEach items="${allServerMap}" var="item">
						<c:set var="serverStatus" value="${item.value.currentCount/item.value.maxCount}"/>
						<%--非测试服务器才展示 --%>
						<c:if test="${!item.value.test}">
							<span class="serverItem">
								<c:if test="${!item.value.maintain}">
									<c:if test="${serverStatus >= 1}">
										<span class="itemBg">${item.value.name}[<font color="#f43936">爆满</font>]</span>
									</c:if>
									<c:if test="${serverStatus < 1}">
										<a href="${ctx}/games/fm.php?c=${item.key}" target="_blank" class="itemBg">${item.value.name} 
											<c:if test="${serverStatus < 0.9}">[<font color="#57f382">空闲</font>]</c:if>
											<c:if test="${serverStatus >= 0.9}">[<font color="yellow">火爆</font>]</c:if>
										</a>
									</c:if>
								</c:if>
								<c:if test="${item.value.maintain}">
									<span class="itemBg">${item.value.name}[<font color="#999999">待开</font>]</span>
								</c:if>
							</span>
						</c:if>
						</c:forEach>
					</div>
				</div>
			</c:if>
			</div></div>
			
		<div class="n_contents" id="Zoom">
		</div>
	</div>
</body>
</html>