<%@page import="com.cf.fuer.CommonConstants"%>
<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>

<html>
<head>
<c:if test="${empty article}">
	<title>${articleType.label}</title>
</c:if>
<c:if test="${not empty article}">
	<title>${article.title}-${articleType.label}</title>
	<meta name="keywords" content="${article.keywords}" />
	<meta name="description" content="${article.summary}" />
</c:if>
<style type="text/css">
table {
	width: 100%;
}
thead tr {
    background-color: #F1F1F1;
    border-bottom: 1px solid #C5C5C7;
    border-top: 2px solid #C5C5C7;
    color: #333333;
    height: 28px;
    line-height: 28px;
}
thead tr .th1{
	background: url("../images/topbar/branching.jpg") no-repeat scroll right center transparent;
    width: 400px;
}
thead tr .th2{
	background: url("../images/topbar/branching.jpg") no-repeat scroll right center transparent;
}
tbody tr {
    border-bottom: 1px solid #C5C5C7;
    color: #333333;
    height: 24px;
    line-height: 24px;
    text-align: center;
}
</style>
</head>
<body>
	<div class="nav_box">
		<div class="fl">${articleType.label}</div>
		<div class="fr">
			<span>当前位置 > </span> <a href="${ctx}/">首页</a> > <a href="${ctx}${articleType.url}">${articleType.label}</a>
		</div>
	</div>
	<%-- 文章详情 --%>
	<c:if test="${not empty article}">
		<div class="">
			<div class="n_tit">${article.title}</div>
			<div class="n_base">
				发布人：${article.author}&nbsp;&nbsp;&nbsp;&nbsp;点击：${article.click}
			</div>
			<div class="n_contents" id="Zoom">
				<div class="list-text" id="listText-644510">
					${article.content}
				</div>
				<div class="clear">&nbsp;</div>
				<div class="list-text">
					<div class="tag" style="display: none;">&nbsp;</div>
					<div class="list-interaction">&nbsp;</div>
				</div>
			</div>
		</div>
	</c:if>
	
	<%-- 文章列表 --%>
	<c:if test="${empty article}">
		<div class="">
			<div class="tb_box">
				<display:table name="searchResult"	requestURI="${ctx}${articleType.url}" id="article" sort="external">
					<c:set var="articleUrl" value="${ctx}${articleType.url}?id=${article.id}"></c:set>
					<display:column title="主题" sortable="true" class="" sortProperty="title"
					style="text-align: left; padding-left: 30px;" headerClass="th1">
						<c:if test="${article.id == -1}">
							<c:set var="articleUrl" value="${ctx}/handbook/index.php"></c:set>
						</c:if>
						<c:if test="${article.id == -2}">
							<c:set var="articleUrl" value="${ctx}/handbook/index.php?i=-2"></c:set>
						</c:if>
						<a href="${articleUrl}"><c:if test="${article.top}">[<span style="color: red;">置顶</span>]&nbsp;</c:if>${article.title}</a>
					</display:column>
					<display:column title="日期" sortable="true" sortProperty="createDate"	 headerClass="th2">
						<fmt:formatDate value="${article.createDate}" pattern="yyyy/MM/dd" />
					</display:column>
					<display:column title="点击" property="click" class="">
					</display:column>
				</display:table>
			</div>
		</div>
	</c:if>
</body>
</html>

