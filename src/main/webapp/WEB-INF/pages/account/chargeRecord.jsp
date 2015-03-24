<%@page import="com.cf.fuer.util.GameServerUtils"%>
<%@page import="com.cf.fuer.CommonConstants"%>
<%@ include file="/taglibs.jsp"%>
<%@page import="com.cf.fuer.security.AccessHelper"%>
<%@page pageEncoding="utf-8"%>
<c:set var="loginUser" value="<%=AccessHelper.getLoginUser()%>" />

<!DOCTYPE html>
<html>
<head>
<title>充值记录</title>
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
				<div class="r_tit">
					<c:if test="${empty detail}">充值记录</c:if>
					<c:if test="${not empty detail}">订单详情</c:if>
				</div>	
				<c:set var="allStatusMap" value="<%=CommonConstants.CHARGE_STATUS.all() %>" />
				<c:set var="serverMap" value="<%=GameServerUtils.allGameServerMap() %>"></c:set>
				<c:if test="${empty detail}">
					<display:table name="searchResult" class="charge_list"
						requestURI="chargeRecord.php" id="charge" sort="external">
						<display:column title="订单号" sortable="false" property="orderId" href="orderDetails.php" paramId="id"></display:column>
						<display:column title="充值服务器" sortable="true" sortProperty="gameServer">${serverMap[charge.gameServer].name}</display:column>
						<display:column title="充值帐号" sortable="false" property="user"></display:column>
						<display:column title="充值金额" sortable="false">${charge.chargeAmount/100.00} 元</display:column>
						<display:column title="金币" sortable="false">${charge.goldMoney} 金币
							<c:if test="${charge.presentGold != 0}">
								+ ${charge.presentGold} 金币(${charge.presentName})
							</c:if>
						</display:column>
						<display:column title="充值状态" sortable="true" sortProperty="status">${allStatusMap[charge.status]}</display:column>
						<display:column title="订单日期" sortable="true"
							sortProperty="createDate">
							<fmt:formatDate value="${charge.createDate}"
								pattern="yyyy-MM-dd HH:mm" />
						</display:column>
					</display:table>
				</c:if>	
				<c:if test="${not empty detail}">
				<div class="r_content">
				<table width="500" border="1" style="border-collapse: collapse" bordercolor="green" align="center">
						<tr>
							<td align="center">
								交易号
							</td>
							<td align="center">
								${order.dealId}
							</td>
						</tr>
						<tr>
							<td align="center">
								订单号
							</td>
							<td align="center">
								${order.orderId}
							</td>
						</tr>
						<tr>
							<td align="center">
								订单金额
							</td>
							<td align="center">
								${order.chargeAmount/100.00} 元
							</td>
						</tr>
						<tr>
							<td align="center">
								金币
							</td>
							<td align="center">
								${order.goldMoney} 金币
								<c:if test="${order.presentGold != 0}">
									+ ${order.presentGold} 金币(${order.presentName})
								</c:if>
							</td>
						</tr>
						<tr>
							<td align="center">
								充值服务器
							</td>
							<td align="center">
								${serverMap[order.gameServer].name}
							</td>
						</tr>					
						<tr>
							<td align="center">
								充值账号
							</td>
							<td align="center">
								${order.user}
							</td>
						</tr>
						<c:if test="${not empty order.managerName}">		
						<tr>
							<td align="center">
								充值经理名称
							</td>
							<td align="center">
								${order.managerName}
							</td>
						</tr>
						</c:if>
						<tr>
							<td align="center">
								下单时间
							</td>
							<td align="center">
								<fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
						<tr>
							<td align="center">
								付款时间
							</td>
							<td align="center">
								<fmt:formatDate value="${order.payDate}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
						<tr>
							<td align="center">
								订单状态
							</td>
							<td align="center">
							<c:set var="errorStatus" value="<%=CommonConstants.CHARGE_STATUS.PAY_ERROR %>" />
							${allStatusMap[order.status]}
							<c:if test="${not empty order.errCode}">
								<font color="#ff0000">错误代码:${order.errCode}</font>
							</c:if>
						</tr>	
						<c:set var="unpayStatus" value="<%=CommonConstants.CHARGE_STATUS.UNPAY %>" />
						<c:if test="${order.status == unpayStatus}">
							<tr>
								<td align="center" colspan="2" height="30">
									<input name="btn_go" type="image" class="chargeBtn" src="${ctx}/images/user/btn_pay.gif" onclick="window.location.href='${ctx}/account/sendCharge.php?orderId=${order.orderId}'">
								</td>
							</tr>
						</c:if>
					</table>
				</div>	
				</c:if>		
			</div>
		</div>
	</div>
</body>
</html>