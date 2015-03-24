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
		<div class="v_box_tit">活动细则</div>
		<div class="v_box_explain">
			<div class="explain_box">
				<div>
					<strong><span>一．</span>活动时间：</strong>
				</div>
				<div>2012年11月16日——2012年12月10日&nbsp;</div>
				<div>
					<strong>二．奖项设置：</strong>
				</div>
				<div>1、一等奖：1名</div>
				<div>获奖球迷对应俱乐部所在城市5日游，包含一张该球队的主场比赛的门票（等值价额20000元人民币）。</div>
				<div>2、二等奖：2名</div>
				<div>联想thinkpad x1 carbon 一台</div>
				<div>
					<span>3、</span>三等奖：3名
				</div>
				<div>
					The new iPad 一部<span>&nbsp; </span>
				</div>
				<div>4、球迷奖：50名</div>
				<div>送出50件中奖球迷对应的喜爱的豪门真品球衣。&nbsp;</div>
				<div>
					<strong>三．抽奖安排：</strong>
				</div>
				<div>1、所有奖项在活动截止之后的第二天进行抽取。</div>
				<div>2、以上活动由上海同甘律师事务所全程监督。</div>
				<div>3、本次活动解释权归属句多网络科技（上海）有限公司。</div>
				<div>&nbsp;</div>
				<div style="text-align: center">
					<strong><span style="font-size: large">律 师 声 明</span>
					</strong>
				</div>
				<div>
					<br> &nbsp;&nbsp;&nbsp;&nbsp;
					受句多网络科技（上海）有限公司委托，上海同甘律师事务所吴艳萍律师、刘晓旻律师发表律师声明如下： <br>
					&nbsp;&nbsp;&nbsp;&nbsp;
					句多网络科技（上海）有限公司是经法定程序依法注册成立的企业。句多网络科技（上海）有限公司负责开发运营的网站是经法定程序设立的网站。句多网络科技（上海）有限公司网站上刊载的所有内容，包括但不限于文字报道、图片、声音、录像、图表、标志、标识、广告、商标、商号、域名、软件、程序、版面设计、专栏目录与名称及内容分类标准，均受《中华人民共和国著作权法》、《中华人民共和国商标法》、《中华人民共和国专利法》及适用之国际公约中有关著作权、商标权、专利权及其他财产所有权法律的保护，著作权为句多网络科技（上海）有限公司及其相关权利人专属所有或持有。未经书面许可，任何报刊、网站或个人均不得转载或者摘编。
					<br> &nbsp;&nbsp;&nbsp;
					任何单位或个人将句多网络科技（上海）有限公司运营网站上提供的内容用于商业、广告、营利活动或潜在营利活动目的时，需与权利人签订书面授权许可合同。并按有关国际公约和中华人民共和国法律的有关规定向有关权利人支付报酬。
					<br>
					对于违反上述要求，涉嫌构成侵权行为的，句多网络科技（上海）有限公司将有权予以制止并委托律师通过诉讼追究侵权人的刑事和民事法律责任。 <br>
					<br> 上海同甘律师事务所<br> 吴艳萍&nbsp; 律师<br> 刘晓旻&nbsp; 律师<br>
					二〇一二年十一月&nbsp;&nbsp; 日<br> &nbsp;
				</div>
				<p>&nbsp;</p>
			</div>
			<div class="close_box">
				<input type="submit" name="button" id="button" value="关闭窗口"
					onclick="javascript:window.close();">
			</div>
		</div>
	</div>
</body>
</html>