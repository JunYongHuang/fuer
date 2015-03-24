<%@page import="com.cf.fuer.util.GameServerUtils"%>
<%@page import="com.cf.fuer.CommonConstants"%>
<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>充值中心</title>
<meta name="titleImg" content="${ctx}/images/user/charge.gif" />
<style type="text/css">
.body{
	background:none;
	min-height:450px;
}
.platPay{
	float: left;
	display: block;
	width: 125px;
	height: 40px;
	line-height: 50px;
	padding-left: 10px;
	margin-right: 10px;
	background: #fff;
	cursor: pointer;
}
.platPay input {
	float: left;
	margin-top: 15px;
	margin-right: 5px;
	cursor: pointer;
}
.platPay img {
	float: left;
	width: 100px;
	height: 35px;
	margin-top: 3px;
}
</style>
</head>
<body>
<c:if test="${empty step }">
	<c:set var="step" value="1" />
</c:if>
	<div class="step">
	</div>
	<c:set var="serverMap" value="<%=GameServerUtils.allGameServerMap() %>"></c:set>
		<c:if test="${step == 1}">
			<form:form method="post" modelAttribute="chargeForm" name="chargeForm">
				<%@ include file="/messages.jsp"%>
				<div class="pay_info t3">
					<div style="color: #000; font-size: 18px;height:25px; text-align: center; margin: 5px;">
					<div style="color: #97161A; float: left; width: 332px;">输入充值信息</div>
					<div style="float: left; width: 332px;">信息确认</div>
					<div style="float: left; width: 300px;">充值成功</div></div>
					<div class="pay_area">
					<span class="err"><form:errors	path="*" /> </span>
						<table width="800" border="0" cellpadding="10" cellspacing="10">
							<tbody>
								<tr>
									<th width="100">充值服务器：</th>
									<td>
									<form:select path="gameServer" onchange="changeAcitvity();">
										<form:option value="">--请选择--</form:option>
										<c:forEach items="${serverMap}" var="item">
											<c:if test="${!item.value.test}">
												<form:option value="${item.value.code}">${item.value.name}</form:option>
											</c:if>
										</c:forEach>
									</form:select>&nbsp;充值前请建好此服的游戏角色</td>
								</tr>
								<tr>
									<th>充值账号：</th>
									<td><form:input path="user"/>请输入您想好充值的通行证账号</td>
								</tr>
								<tr>
									<th>确认账号：</th>
									<td><form:input path="confirmUser"/>请确认您想充值的通行证账号</td>
								</tr>
								<tr>
									<th>充值金额：</th>
									<td>
									<form:select path="chargeAmount" onchange="changeGold();">
										<form:option value="0">--请选择--</form:option>
										<form:option value="100">1</form:option>
										<form:option value="1000">10</form:option>
										<form:option value="2000">20</form:option>
										<form:option value="5000">50</form:option>
										<form:option value="10000">100</form:option>
										<form:option value="20000">200</form:option>
										<form:option value="50000">500</form:option>
										<form:option value="80000">800</form:option>
										<form:option value="100000">1000</form:option>
										<form:option value="200000">2000</form:option>
										<form:option value="500000">5000</form:option>
										<form:option value="1000000">10000</form:option>
										<form:option value="-1">其它金额</form:option>
									</form:select>&nbsp;
									<form:input path="otherChargeAmount" class='${hideClass}' maxlength="9" onkeyup="changeGold();" style="width:80px;"/>&nbsp;元
									</td>
								</tr>
								<tr>
									<th>金币：</th>
									<td><span id="goldMoney" style="color: red;">0</span>&nbsp;金币 + 返现 <span id="presentGold" style="color: red;">0</span> 金币</td>
								</tr>
								<tr>
									<th>当前活动：</th>
									<td><span id="acti">无</span></td>
								</tr>
								<tr>
									<th></th>
									<td><input type="image" name="nextBtn" class="chargeBtn"
										src="${ctx}/images/tab/nextBtn.png">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
<script type='text/javascript' src='${ctx}/dwr/interface/AjaxUtil.js'></script>
<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
	<script type="text/javascript">
	changeAcitvity();
		function changeAcitvity(){
			var code = get("gameServer").value;
			AjaxUtil.getPresentGold(code,1000000,function(result) {
				if(result == 0){
					get("acti").innerHTML = "无";
				}else{
					get("acti").innerHTML = "活动返现   100(含)~500返5%；500(含)~1000返10%；1000(含)~5000返15%；5000(含)以上返20%";
				}
				changeGold();
			});
		}
			function changeGold() {
				var chargeAmount = get("chargeAmount").value;
				var rmbMoney = 0;
				if(chargeAmount == -1){
					get('otherChargeAmount').className='';
					var otherChargeAmount = get('otherChargeAmount').value;
					if(isInteger(otherChargeAmount)){
						rmbMoney = otherChargeAmount * 100;
					}
				}else {
					get('otherChargeAmount').className='hide';
					rmbMoney = chargeAmount;
				}
				if(rmbMoney == 0){
					get('goldMoney').innerHTML=0;
					get('presentGold').innerHTML=0;
				}else {
					get('goldMoney').innerHTML=rmbMoney/10 ;
					var code = get("gameServer").value;
					AjaxUtil.getPresentGold(code,rmbMoney,function(result) {
						get('presentGold').innerHTML=result;
					});
				}
			}
			function isInteger(str){
				var regu = /^[0-9]{1,}$/;
				return regu.test(str);
			}
			
			function checkPayType(id){
				get(id).checked="checked";
			}
			</script>
	</c:if>
		<c:if test="${step == 2}">
			<script type="text/javascript">
				function checkPayType(id){
					get(id).checked="checked";
					get("payFormName").value = id +"submit";
				}
				
				function submitPay(){
					var payFormName = get("payFormName").value;
					document.forms[payFormName].submit();
				}
			</script>
			<div class="pay_info t3">
			<div style="color: #000; font-size: 18px;height:25px; text-align: center; margin: 5px;">
					<div style="float: left; width: 332px;">输入充值信息</div>
					<div style="color: #97161A; float: left; width: 332px;">信息确认</div>
					<div style="float: left; width: 300px;">充值成功</div></div>
				<div class="pay_area2">
					<table width="800" border="0" cellpadding="10" cellspacing="10">
						<tbody>
							<tr>
								<th>订单编号：</th>
								<td>${order.orderId}</td>
							</tr>
							<tr>
								<th>充值服务器：</th>
								<td>${serverMap[order.gameServer].name}</td>
							</tr>
							<tr>
								<th width="100">充值账号：</th>
								<td>${order.user}</td>
							</tr>
							<c:if test="${not empty order.managerName}">
								<tr>
									<th width="100" align="center">经理名称：</th>
									<td>${order.managerName}</td>
								</tr>
							</c:if>
							<tr>
								<th>充值金额：</th>
								<td><strong style="color: red;"> 
								<fmt:formatNumber value="${order.chargeAmount/100.00}" pattern="#0.00"/>
								</strong>&nbsp;元</td>
							</tr>
							<tr>
								<th>金币：</th>
								<td><strong style="color: red;">${order.goldMoney}</strong>&nbsp;金币 + ${order.presentName}返现 <strong style="color: red;">${order.presentGold}</strong>&nbsp;金币</td>
							</tr>
							<tr>
								<th>支付方式：</th>
								<td>
									<input type="hidden" id="payFormName" value="99billsubmit">
									<div class="platPay">
										<input type="radio" name="payType" value="2" id="99bill" checked="checked" onclick="checkPayType('99bill')">
										<img alt="快钱支付" src="${ctx}/images/pay/99bill.png" title="快钱支付" onclick="checkPayType('99bill')">
									</div>
									<div class="platPay">
										<input type="radio" name="payType" value="1" id="alipay" onclick="checkPayType('alipay')">
										<img alt="支付宝支付" src="${ctx}/images/pay/alipay.gif" title="支付宝支付" onclick="checkPayType('alipay')">
									</div>
								</td>
							</tr>
							<tr>
								<th>&nbsp;</th>
								<td>
								<%@ include file="./send.jsp"%>
								<%@ include file="./alipay/alipayapi.jsp"%>
								<input name="btn_go" type="image" class="chargeBtn" src="${ctx}/images/tab/payBtn.png" onclick="submitPay();">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
		<c:if test="${step == 3}">
			<div class="pay_info t3">
			<div style="color: #000; font-size: 18px;height:25px; text-align: center; margin: 5px;">
					<div style="float: left; width: 332px;">输入充值信息</div>
					<div style="float: left; width: 332px;">信息确认</div>
					<div style="color: #97161A; float: left; width: 300px;">充值成功</div></div>
				<div align="center" style="margin: 20px;">
					<h2 align="center" style="margin-bottom: 5px;">支付结果</h2>(<a href="${ctx}/account/chargeRecord.php" style="color: #0166C2;">查看我的充值记录</a> )
			    	<table width="500" border="1" style="border-collapse: collapse; margin-top: 5px; " bordercolor="#C5C5C7"
			    	 align="center">
						<tr>
							<td align="center">
								交易号
							</td>
							<td align="center">
								${charge.dealId}
							</td>
						</tr>
						<tr>
							<td align="center">
								订单号
							</td>
							<td align="center">
								${charge.orderId}
							</td>
						</tr>
						<tr>
							<td align="center">
								订单金额
							</td>
							<td align="center">
								${charge.chargeAmount/100.00} 元
							</td>
						</tr>	
						<tr>
							<td align="center">
								充值服务器
							</td>
							<td align="center">
								${serverMap[charge.gameServer].name}
							</td>
						</tr>					
						<tr>
							<td align="center">
								充值账号
							</td>
							<td align="center">
								${charge.user}
							</td>
						</tr>
						<c:if test="${not empty charge.managerName}">
								<tr>
									<th align="center">经理名称：</th>
									<td align="center">${charge.managerName}</td>
								</tr>
						</c:if>
						<tr>
							<td align="center">
								下单时间
							</td>
							<td align="center">
								<fmt:formatDate value="${charge.createDate}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
						<tr>
							<td align="center">
								付款时间
							</td>
							<td align="center">
								<fmt:formatDate value="${charge.payDate}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
						<tr>
							<td align="center">
								处理结果
							</td>
							<td align="center">
							<c:set var="allStatusMap" value="<%=CommonConstants.CHARGE_STATUS.all() %>" />
							<c:set var="errorStatus" value="<%=CommonConstants.CHARGE_STATUS.PAY_ERROR %>" />
							${allStatusMap[charge.status]}
							<c:if test="${not empty charge.errCode}">
								<font color="#ff0000">错误代码:${charge.errCode}</font>
							</c:if>
						</tr>	
					</table>
	</div>
			</div>
		</c:if>
	<div class="note">
		<strong>网银充值说明：</strong><br>
		1、请您关闭所有屏蔽弹出窗口之类的功能，否则在线支付将无法继续，比如：3721、上网助手、google toolbar、alexa
		toolbar、baidu等； <br> 2、如果您用信用卡支付，请确认该信用卡的网上交易限额大于等于您的充值金额； <br>
		3、如果有疑问，请联系我们在线客服或拨打客服电话。
	</div>
</body>
</html>