<%@page import="com.cf.fuer.util.GameServerUtils"%>
<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
<style type="text/css">
#spreadBox{
	margin: auto;
	width: 600px;
	padding: 10px;
	border-radius: 0.5em 0.5em 0.5em 0.5em;
	position: absolute;
	top: 150px;
	left: 180px;
	background-color: #DDDDDD;
	display: none;
}
.btn{
	width:60px;
	height:24px;
}
</style>
<title>我的推广</title>
</head>
<body>
	<div class="v_box">
		<div class="v_box_tit">我的推广</div>
		<div class="v_box_explain">

			<div class="v_left">
				<ul class="lmenu">
					<li><a href="${ctx}/account/myspread.php">我的推广</a></li>
					<li><a href="${ctx}/account/adusers.php">推广用户</a></li>
					<li><a href="${ctx}/account/adcharges.php">推广充值</a></li>
				</ul>
			</div>
			<div class="v_right">
				<p>我的推广地址:&nbsp;&nbsp;<b>http://www.yyfuer.com/info/<span id="spreadUrl">${mySpread.addressCode}</span>.php</b>&nbsp;&nbsp;&nbsp;
				[<a href="#" style="color: blue;" onclick="show('spreadBox');">修改</a>]				
				</p>
				<p>推广点击数量:<b>&nbsp;&nbsp;${mySpread.clickCount} 次</b></p>
				<p>推广注册用户:<b>&nbsp;&nbsp;${mySpread.userCount} 人</b></p>
				<p>推广充值总额:<b>&nbsp;&nbsp;${mySpread.chargeAmount/100.00} 元</b></p>
			</div>
		</div>
	</div>


	<div id="spreadBox">
		<div>推广链接地址设置</div>
		<div style="margin: 10px;">
			<table class="tbl_list">
				<tr>
					<td colspan="2" style="font-weight: bold;color: red;">注意: 链接地址修改后, 原有推广用户将会丢失, 请谨慎修改!!!</td>
				</tr>
				<tr>
					<th>链接地址</th>
					<td>http://www.yyfuer.com/info/<input type="text"
						id="addressCode" value="${mySpread.addressCode}" />.php</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div>
			<input class="btn" type="button" value="保存" onclick="saveSpread();">
			<input class="btn" type="button" value="取消" onclick="hide('spreadBox');">
		</div>
	</div>
	<script type='text/javascript'
		src='${ctx}/dwr/interface/AdminAjaxUtil.js'></script>
	<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
	<script type="text/javascript">
		function saveSpread(){
			var addressCode = get('addressCode').value;
			AdminAjaxUtil.updateSpreadCode(addressCode, function(result){
				if(result == ''){
					alert("保存成功!");
					get("spreadUrl").innerHTML=addressCode;
					hide('spreadBox');
				}else{
					alert(result);
				}
			});
		}
	</script>
</body>
</html>