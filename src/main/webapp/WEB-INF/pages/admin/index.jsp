<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理</title>
</head>
<body>
	<div class="tbox">
		<div class="tbox_head">快捷工具</div>
		<div class="tbox_content">

			<ul class="ico_box">
				<li class="ico_img"><a href="userList.php"><img
						src="../images/admin/icon_t16.gif" width="82" height="63"> </a></li>
				<li class="ico_tit"><a href="userList.php">用户管理</a></li>
				<li class="ico_tit"><a href="userUpdate.php">新增用户</a></li>
			</ul>
			<ul class="ico_box">
				<li class="ico_img"><a href="articleList.php"><img
						src="../images/admin/icon_t11.gif" width="82" height="63"> </a></li>
				<li class="ico_tit"><a href="articleList.php">文章管理</a></li>
				<li class="ico_tit"><a href="articlePub.php">文章发布</a></li>
			</ul>
			<ul class="ico_box">
				<li class="ico_img"><a href="serverList.php"><img
						src="../images/admin/icon_t9.gif" width="82" height="63"> </a></li>
				<li class="ico_tit"><a href="serverList.php">服务器管理</a></li>
				<li class="ico_tit"><a href="gameServer.php">新增服务器</a></li>
			</ul>
			<ul class="ico_box">
				<li class="ico_img"><a href="chargeRecordList.php"><img
						src="../images/admin/icon_t10.gif" width="82" height="63"> </a></li>
				<li class="ico_tit"><a href="chargeRecordList.php">充值记录</a></li>
			</ul>
		</div>
	</div>
</body>
</html>