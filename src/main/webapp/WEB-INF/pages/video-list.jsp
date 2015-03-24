<%@page pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>

<div id='video-box'>
	<div id="video-head">
		<img alt="够足球精彩视频" src="${ctx}/images/video/video-head.jpg"
			width="722" height="34px"/>
	</div>
	<c:if test="${not empty videoPage}">
		<div id="video_player">
			<c:set var="videoUrl"
				value="http://static.youku.com/v/swf/qplayer.swf?VideoIDS=XNTE4NzQ1MjU2&winType=adshow&isAutoPlay=false"></c:set>
			<c:if test="${not empty videoId}">
				<c:set var="videoUrl"
					value="http://static.youku.com/v/swf/qplayer.swf?VideoIDS=${videoId}&winType=adshow&isAutoPlay=true"></c:set>
			</c:if>
			<embed src="${videoUrl}" quality="high" width="722" height="396"
				align="middle" allowScriptAccess="sameDomain" id="youku_player"
				type="application/x-shockwave-flash">
			</embed>
		</div>
	</c:if>

	<div id="video-list">
		<a class="pre"></a>
		<div id="wai_box">
			<div class="video-list_box" style="left: 0px;">
				<ul>
					<li><a href="#" id="XNDc0MTAzMTYw" title="够足球宣传片"
						class="images"><img src="${ctx}/images/video/video01.jpg"
							alt="够足球宣传片" width="142" height="102"/> </a><span>够足球宣传片</span>
					</li>
					<li><a href="#" id="XNTE4NzQ1MjU2" title="足球宝贝视频宣传片"
						class="images"><img src="${ctx}/images/video/video04.jpg"
							alt="足球宝贝视频宣传片" width="142" height="102"/> </a><span>足球宝贝视频宣传片</span>
					</li>
					<li><a href="#" id="XNTA4OTA1MzYw" title="够足球足球宝贝宣传片"
						class="images"><img src="${ctx}/images/video/video02.jpg"
							alt="够足球足球宝贝宣传片" width="142" height="102"/> </a><span>够足球足球宝贝宣传片</span>
					</li>
					<li><a href="#" id="XNTAxNzkxNzU2" title="屌丝那点事-够足球游戏主题曲"
						class="images"><img src="${ctx}/images/video/video03.jpg"
							alt="屌丝那点事-够足球游戏主题曲" width="142" height="102"/> </a><span>屌丝那点事-够足球游戏主题曲</span>
					</li>
				</ul>
			</div>
		</div>
		<a class="next"></a>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		var page = 1;//初始化当前的版面为1
		var $show = $("#video-list").find(".video-list_box");//找到图片展示区域

		var video_count = $show.find("ul>li").length;
		if (video_count > 4) {
			nav();
		}
		//找到图片展示区域外围的div
		var $li_width = $("#video-list .video-list_box li").outerWidth(true);
		function nav() {
			if (page == 1) {
				$("#video-list").find(".pre").hide().siblings(".next").show();
			} else if (page == video_count) {
				$("#video-list").find(".next").hide().siblings(".pre").show();
			} else {
				$("#video-list").find(".pre").show().siblings(".next").show();
			}
		}
		$("#video-list").find(".next").click(
				function() {
					//首先判断展示区域是否处于动画
					if (!$show.is(":animated")) {
						$show.animate({
							left : '-=' + $li_width
						}, "normal");
						page++;
						nav();
						$number = page - 1;
						$("#video-list").find("li:eq(" + $number + ")")
								.addClass("now").siblings("li").removeClass(
										"now");
						return false;
					}
				})
		$("#video-list").find(".pre").click(
				function() {
					if (!$show.is(":animated")) {
						$show.animate({
							left : '+=' + $li_width
						}, "normal");
						page--;
						nav();
						$number = page - 1;
						$("#video-list").find("li:eq(" + $number + ")")
								.addClass("now").siblings("li").removeClass(
										"now");
					}
					return false;
				})
		$("#video-list .video-list_box ul li a")
				.click(
						function() {
							var videoId = $(this).attr('id');
							$('#video_player')
									.html(
											'<embed src="http://static.youku.com/v/swf/qplayer.swf?VideoIDS='
													+ videoId
													+ '&winType=adshow&isAutoPlay=true" '
													+ ' quality="high" width="722" height="396" align="middle"  '
													+ +' allowScriptAccess="sameDomain"  '
													+ ' id="youku_player"'
													+ ' type="application/x-shockwave-flash"></embed>');
							var isPage = '${videoPage}';
							if (isPage) {
								$(this).parents("li").addClass("now").siblings(
										"li").removeClass("now");
								return false;
							} else {
								$(this).attr('href',
										"${ctx}/videos.php?vid=" + videoId);
							}
						});

	});
</script>