
<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>够足球精彩视频</title>
</head>
<body>
	<div class="nav_box">
		<div class="fr">
			<span>当前位置:</span> <a href="${ctx}/">首页</a> / <a href="${ctx}/videos.php">够足球精彩视频</a>
		</div>
	</div>
	<div class="content_box t3" style="padding:0px;">	
		<c:set var="videoPage" value="true"></c:set>	
		<%@ include file="/WEB-INF/pages/video-list.jsp"%>
	</div>
</body>
</html>