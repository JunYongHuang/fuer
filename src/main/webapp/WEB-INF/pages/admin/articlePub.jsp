<%@page import="com.cf.fuer.CommonConstants"%>
<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文章发布</title>
<script type="text/javascript"
	src="${ctx}/scripts/ueditor/editor_config.js"></script>
<script type="text/javascript"
	src="${ctx}/scripts/ueditor/editor_all_min.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/ueditor/themes/default/ueditor.css">
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="articleList.php">文章列表</a>
			&gt;&gt; 文章发布
		</div>
		<div class="box_content">
			<form:form method="post" modelAttribute="articleForm" name="articleForm">
				<%@ include file="/messages.jsp"%>
				<span class="err"><form:errors path="*" /> </span>

				<table width="100%" border="0" cellpadding="0" cellspacing="1"
					class="tbl_frm">
					<tbody>
						<tr>
							<th width="10%">文章标题：</th>
							<td>
							<form:input path="title" size="48" maxlength="120" class="ipt"/>
						</tr>
						<tr>
							<th>文章分类：</th>
							<td>
							<form:select path="type">
								<form:option value="-1" selected="selected">--主分类--</form:option>
									<c:forEach items="<%=CommonConstants.ARTICLE_TYPE.values()%>"
										var="type">
											<form:option value="${type.type}">${type.label}</form:option>
									</c:forEach>
							</form:select>
							</td>
						</tr>

						<tr>
							<th>摘自：</th>
							<td>
							<form:input path="source" maxlength="32" class="ipt"/>
							</td>
						</tr>
						<tr>
							<th>发布人：</th>
							<td>
								<form:input path="author" maxlength="32" class="ipt"/>
							</td>
						</tr>
						<tr>
							<th><label> 关&nbsp;键&nbsp;字：</label>
							</th>
							<td>
							<form:input path="keywords" maxlength="64" class="ipt"/>&nbsp;&nbsp;&nbsp;&nbsp;多个关键字用空格格开！<span
								class="u_err"></span></td>
						</tr>
						<tr>
							<th>文章提要：</th>
							<td>
								<form:textarea path="summary" cols="60" rows="4" id="summary" class="txt"/>
							</td>
						</tr>
						<tr>
							<th>参数：</th>
							<td>
								<form:checkbox path="top" value="1"/><label for="top">置顶</label>
							</td>
						</tr>
						<tr>
							<th>文章内容：<span style="color: #FF0000">*</span>
							</th>
							<td>
								<div id="area_contents">
									<script type="text/plain" id="content" name="content">
										${articleForm.content}
									</script>
									<script type="text/javascript">
										var editor = new baidu.editor.ui.Editor();
										editor.render("content");
									</script>
								</div>
							</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td>
							<form:hidden path="id"/>
							<input name="btn_save" type="submit" id="btn_save"
								value="  提交  ">
							</td>
						</tr>
					</tbody>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>