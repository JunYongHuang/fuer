<%@page import="com.cf.fuer.util.GameServerUtils"%>
<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>充值详情-后台管理</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="chargeRecordList.php">充值记录</a>
			&gt;&gt; 充值详情
		</div>
		
		<c:set var="allStatusMap" value="<%=CommonConstants.CHARGE_STATUS.all() %>" />
		<c:set var="serverMap" value="<%=GameServerUtils.allGameServerMap() %>"></c:set>
		<div class="box_content">
				<table width="800" border="1" style="border-collapse: collapse" bordercolor="#88DD88" align="center" class="tbl_frm">
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
								订单状态
							</td>
							<td align="center">
							${allStatusMap[order.status]}
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
								经理名称
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
								交易号
							</td>
							<td align="center">
								${order.dealId}
							</td>
						</tr>
						<tr>
							<td align="center">
								交易时间
							</td>
							<td align="center">
								${order.dealTime}
							</td>
						</tr>
						<tr>
							<td align="center">
								充值金额
							</td>
							<td align="center">
								<c:if test="${order.payAmount != 0}">
									${order.payAmount/100.00} 元
								</c:if>
							</td>
						</tr>
						<tr>
							<td align="center">
								手续费
							</td>
							<td align="center">
								<c:if test="${order.fee != 0}">
									${order.fee/100.00} 元
								</c:if>
							</td>
						</tr>
						<tr>
							<td align="center">
								付款账号
							</td>
							<td align="center">
								<c:if test="${order.payAmount != 0}">
									${order.payUser}
								</c:if>
							</td>
						</tr>
						<tr>
							<td align="center">
								支付方式
							</td>
							<td align="center">
							<c:if test="${not empty order.payType}">
								${order.payType}  (一般为00，代表所有的支付方式。如果是银行直连商户，该值为10,该值与提交时相同)
							</c:if>
							</td>
						</tr>
						<tr>
							<td align="center">
								银行代码
							</td>
							<td align="center">
							<c:if test="${not empty order.bankId}">
								${order.bankId}  (如果payType为00，该值为空；如果payType为10,该值与提交时相同)
							</c:if>
							</td>
						</tr>
						<tr>
							<td align="center">
								银行交易号
							</td>
							<td align="center">
							<c:if test="${not empty order.bankDealId}">
								${order.bankDealId}  (快钱交易在银行支付时对应的交易号，如果不是通过银行卡支付，则为空)
							</c:if>
							</td>
						</tr>
						<tr>
							<td align="center">
								错误代码
							</td>
							<td align="center">
							<c:if test="${not empty order.errCode}">
								${order.errCode}  (请参照《人民币网关接口文档》最后部分的详细解释,正常情况下此字段为空)
							</c:if>
							</td>
						</tr>
						<tr>
							<td align="center">
								返回结果的时间
							</td>
							<td align="center">
								<fmt:formatDate value="${order.payDate}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
						
						<tr>
							<td align="center">
								充值到游戏服务器的时间
							</td>
							<td align="center">
								<fmt:formatDate value="${order.chargeDate}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
					</table>
		</div>
	</div>
</body>
</html>