<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" type="text/css" href="${ctx}/styles/vote.css"
	title="default" />
<title>快来投票选出你心目中的豪门球队，还有机会赢得超值大礼</title>
</head>
<body>
	<div class="box_note">
		<a href="voteExplain.php" target="_blank"><img
			src="${ctx}/images/vote/btn_note.gif" width="83" height="21"
			alt="活动细则"> </a>
	</div>
	<div class="v_box">
		<div class="v_box_tit">
			<div class="fl">投票： 你支持哪支球队?</div>
			<div class="fr">

				<!-- Baidu Button BEGIN -->
				<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
					<span class="bds_more">分享到：</span> <a class="bds_qzone"
						title="分享到QQ空间" href="#"></a> <a class="bds_tsina" title="分享到新浪微博"
						href="#"></a> <a class="bds_tqq" title="分享到腾讯微博" href="#"></a> <a
						class="bds_renren" title="分享到人人网" href="#"></a> <a
						class="bds_t163" title="分享到网易微博" href="#"></a> <a
						class="shareCount" href="#" title="累计分享13次">13</a>
				</div>
				<script type="text/javascript" id="bdshare_js"
					data="type=tools&amp;uid=6518593"
					src="http://bdimg.share.baidu.com/static/js/bds_s_v2.js?cdnversion=376110"></script>
				<script type="text/javascript" id="bdshell_js"
					src="http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=376110"></script>
				<script type="text/javascript">
						document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion="
								+ Math.ceil(new Date() / 3600000)
					</script>
				<!-- Baidu Button END -->

			</div>
		</div>
		<div class="v_box_body">
			<ul>
				<c:forEach items="${voteItems}" var="item">
					<li><label> <img src="${ctx}/images/vote/${item.img}"
							width="170" height="170" alt="${item.title}"><br> <input
							name="select" type="radio" value="${item.id}"
							onclick="changeValue(${item.id});">${item.title}</label>
					</li>
				</c:forEach>
			</ul>
			<div class="cl"></div>
		</div>

		<div class="v_box_foot">
			<input type="button" class="btn_vote" id="btn_vote" value=""
				onclick="showWin();">
		</div>
	</div>

	<div id="TB_window" class="ui-draggable"
		style="margin-left: -400px; width: 800px; margin-top: -180px; display: none;">
		<div id="TB_title">
			<div id="TB_ajaxWindowTitle">完成个人信息，就有机会赢得笔记本，智能手机，真品球衣等超值大礼！！！</div>
			<div id="TB_closeAjaxWindow">
				<a href="#" id="TB_closeWindowButton" onclick="closeWin();"></a>
			</div>
		</div>
		<div id="TB_ajaxContent" style="height: 360px">

			<div id="apDiv1" style="display: none;">
				<div class="m_box">
					<img src="${ctx}/images/vote/bg_02.png" width="425" height="225">
					<div class="close" onclick="tb_remove();"></div>
					<div class="share_box">
						<div class="share">
							<!-- JiaThis Button BEGIN -->
							<div class="jiathis_style"><span class="jiathis_txt">分享到：</span>
							<a class="jiathis_button_qzone"></a>
							<a class="jiathis_button_tsina"></a>
							<a class="jiathis_button_tqq"></a>
							<a class="jiathis_button_renren"></a>
							<a class="jiathis_button_kaixin001"></a>
							<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jiathis_separator jtico jtico_jiathis" target="_blank"></a>
							<a class="jiathis_counter_style"></a>
							</div>
							<script type="text/javascript" >
							var jiathis_config={
								url:"http://t.cn/zjAqQBB",
								summary:"快来投票选出你心目中的豪门球队，还有机会赢得超值大礼",
								title:"够足球—职业足球经理游戏 ##",
								ralateuid:{
									"tsina":"3089182323"
								},
								hideMore:false
							}
							</script>
							<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
							<!-- JiaThis Button END -->

						</div>
						<div class="weibo">
							<iframe allowtransparency="" border="0" frameborder="0"
								height="22" marginheight="0" marginwidth="0" scrolling="no"
								src="http://widget.weibo.com/relationship/followbutton.php?width=200&amp;height=22&amp;uid=3089182323&amp;style=1&amp;btn=red&amp;dpc=1"
								style="width: 64px; height: 22px;" width="200"></iframe>
						</div>
					</div>
				</div>
			</div>
			<br>

			<form name="frm_data" id="frm_data">
				<table width="800" border="0" cellpadding="0" cellspacing="0"
					class="tbl_data">
					<tbody>
						<tr>
							<th width="143"></th>
							<td width="657" colspan="2"><span
								class="err" id="error"></span></td>
						</tr>
						<tr>
							<th width="143">姓名：</th>
							<td width="657" colspan="2"><input name="uname" type="text"
								class="ipt" id="uname" maxlength="32"> &nbsp;<span
								class="note"></span></td>
						</tr>
						<tr>
							<th>手机：</th>
							<td colspan="2"><input name="mobile" type="text" class="ipt"
								id="mobile" maxlength="16"> &nbsp;<span class="note"></span>
							</td>
						</tr>
						<tr>
							<th>邮箱：</th>
							<td colspan="2"><input name="email" type="text" class="ipt"
								id="email" maxlength="32"> &nbsp;<span class="note">请填写真实的邮箱地址,用于接收抽奖结果</span>
							</td>
						</tr>
						<tr>
							<th>地址：</th>
							<td colspan="2"><input name="addr" type="text" class="ipt"
								id="addr" maxlength="64"> &nbsp;<span class="note"></span>
							</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td colspan="2"><strong> <input type="hidden"
									value="-1" id="voteSelect" name="voteSelect"> <input
									type="button" id="btn_save" class="btn_ok" value="" onclick="saveVoteWithReg();"> <span
									class="loadding"><img
										src="${ctx}/images/vote/loading.gif" align="absmiddle">&nbsp;请稍后，正在处理……</span>
							</strong></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
<script type='text/javascript' src='${ctx}/dwr/interface/AjaxUtil.js'></script>
<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
	<script type="text/javascript">
		function closeWin(){
			var answer=window.confirm("确定要退出吗?\n\n退出你将失去赢取丰厚奖品的机会,同时此次投票也将无效!");
			if(answer==true){
				tb_remove();
			}
		}
		function tb_remove(){
			hide('TB_window');
			window.location.href="${ctx}/voteDetail.php";
		}
		function changeValue(selectValue){
			get("voteSelect").value = selectValue;
		}
		function showWin(){
			var select = get('voteSelect').value;
			if(select == -1){
				alert("请选择您支持的球队!");
			}else{
				show('TB_window');
			}
		}
		
		function saveVoteWithReg(){
			var selectValue = get("voteSelect").value;
			var uname = get('uname').value;
			var email = get('email').value;
			var mobile = get('mobile').value;
			var addr = get("addr").value;
			AjaxUtil.voteWithReg(selectValue, uname, email, mobile, addr,function(result) {
				if(result == ''){
					show('apDiv1');
				}else{
					get("error").innerHTML = result;
				}
			});
			return false;
		}
	</script>
</body>
</html>