<%@page import="java.util.Date"%>
<%@ include file="/taglibs.jsp"%>
<%@page import="com.cf.fuer.security.AccessHelper"%>
<%@page pageEncoding="utf-8"%>
<c:set var="loginUser" value="<%=AccessHelper.getLoginUser()%>" />

<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" type="text/css" href="${ctx}/styles/vote.css"
	title="default" />
<title>快来投票选出你心目中的豪门球队，还有机会赢得超值大礼</title>
</head>
<body>
	<div class="v_box">



		<div class="v_box_tit">你支持哪支球队? - 投票结果(共${totalVote}票)</div>
		<div class="v_box_view">
			<table border="0" align="center" cellpadding="0" cellspacing="5">
				<c:forEach items="${voteItems}" var="item" varStatus="itemStatus">
					<tr>
						<td width="36" align="center"><img
							src="${ctx}/images/vote/${item.img}"
							width="50" height="50" /></td>
						<td width="123">${itemStatus.index+1} ${item.title}</td>
						<td width="319"><img src='${ctx}/images/vote/bg_vote_item.gif'
							width="${item.voteTotal * 500/totalVote}" height='36' /></td>
						<th width="73" scope="row"><fmt:formatNumber value="${item.voteTotal/totalVote}" type="currency" pattern="0.0%"/></th>
						<td width="80">${item.voteTotal}票</td>
					</tr>
				</c:forEach>	
			</table>
		</div>
	</div>
</body>
</html>