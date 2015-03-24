<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文章列表-后台管理</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="articleList.php">文章列表</a>
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
					<form action="articleList.php" method="post">
						<select name="type">
							<option value="-1">-------不限-------</option>
									<c:forEach items="<%=CommonConstants.ARTICLE_TYPE.values()%>" var="articleType">
										<option value="${articleType.type}" <c:if test="${articleType.type == type}">selected='selected'</c:if>>${articleType.label}</option>
									</c:forEach>
						</select>
						关键字： <input type="text" name="title" value="${title}"> <input
							type="submit" value="提交">
					</form>
				</div>
			</div>
			<%@ include file="/messages.jsp"%>
			<display:table name="searchResult" class="tbl_list"
				requestURI="articleList.php" id="article" sort="external">
				<display:column title="编号" property="id" sortable="true"
					sortProperty="id"></display:column>
				<display:column title="标题" property="title" sortable="true"
					sortProperty="title" href="articlePub.php" paramId="id"
					paramProperty="id"></display:column>
				<display:column title="分类" sortable="true" sortProperty="type">
					<c:set scope="request" var="type" value="${article.type}"></c:set>
					<%=CommonConstants.ARTICLE_TYPE.getTypeLabel((Integer) request.getAttribute("type"))%>
				</display:column>
				<display:column title="点击" property="click" sortable="true"
					sortProperty="click"></display:column>
				<display:column title="摘自" property="source" sortable="true"
					sortProperty="source"></display:column>
				<display:column title="发布人" property="author" sortable="true"
					sortProperty="source"></display:column>
				<display:column title="发布日期" sortable="true"
					sortProperty="createDate">
					<fmt:formatDate value="${article.createDate}"
						pattern="yyyy-MM-dd HH:mm" />
				</display:column>
				<display:column title="置顶" property="top" sortable="true"
					sortProperty="top"></display:column>
				<display:column title="操作" sortable="false">
					<a href="articleRemove.php?id=${article.id}" onclick="return confirmDelete();">删除</a>
				</display:column>
			</display:table>
		</div>
	</div>
</body>
</html>