<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>参与者列表-后台管理</title>
</head>
<body>
	<div class="box">
		<div class="box_head">
			<a href="index.php">管理首页</a> &gt;&gt; <a href="voteList.php">参与者列表</a>
		</div>
		<div class="box_content">
			<div class="box_toolbar">
				<div class="toolbar_btns"></div>
				<div class="toolbar_seacher">
					<form action="voteList.php" method="post">
						姓名： <input type="text" name="uname" value="${uname}"> 邮箱： <input
							type="text" name="uemail" value="${uemail}"> 手机： <input
							type="text" name="umobile" value="${umobile}">地址： <input
							type="text" name="uaddr" value="${uaddr}">
							<input type="submit" value="提交">
					</form>
				</div>
			</div>
			<%@ include file="/messages.jsp"%>
			<div style="margin: 10px 10px;">
			<span><b><a href="${ctx}/voteDetail.php" target="_blank">查看投票结果</a></b></span>
			<span style="float:right;">共&nbsp;<b>${searchResult.fullListSize}</b>&nbsp;条&nbsp;&nbsp;</span></div>
			<display:table name="searchResult" class="tbl_list"	requestURI="voteList.php" id="voteReg" sort="external">
				<display:column title="编号" sortable="true" sortProperty="id" property="id" />
				<display:column title="姓名" sortable="true" sortProperty="uname" property="uname" />
				<display:column title="邮箱" sortable="true" sortProperty="email" property="email" />
				<display:column title="手机" sortable="true" sortProperty="mobile" property="mobile" />
				<display:column title="地址" sortable="true" sortProperty="addr" property="addr" />
				<display:column title="投票日期" sortable="true" sortProperty="regdate" >
					<fmt:formatDate	value="${voteReg.voteDate}" pattern="yyyy-MM-dd HH:mm" />
				</display:column>
			</display:table>
		</div>
	</div>
</body>
</html>