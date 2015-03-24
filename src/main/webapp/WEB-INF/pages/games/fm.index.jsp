<%@page import="java.net.URLEncoder"%>
<%@ include file="/taglibs.jsp"%>
<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${serverName}</title>
</head>

<c:if test="${not empty msg}">
<c:set var="err" value="true"></c:set>
	<span style="color:red;">${msg}</span>
</c:if>
<c:if test="${not empty loginJs}">
	<script type="text/javascript" charset="utf-8" src="${loginJs}"></script>
</c:if>

<%
	Object rechargeUrl = request.getAttribute("rechargeUrl");
	if(rechargeUrl != null){
		try {
			String chargeUrl = URLEncoder.encode(String.valueOf(rechargeUrl), "utf-8");
			request.setAttribute("rechargeUrl",chargeUrl);
		} catch (Exception e) {
			//throw new RuntimeException(e);
		}
	}

	Object loginJs = request.getAttribute("loginJs");
	if(loginJs != null){
		try {
			String jsUrl = URLEncoder.encode(String.valueOf(loginJs), "utf-8");
			request.setAttribute("loginJs",jsUrl);
		} catch (Exception e) {
			//throw new RuntimeException(e);
		}
	}
%>
	
<c:if test="${not empty relogin}">
<c:set var="err" value="true"></c:set>
  <script type="text/javascript">
	fusion2.dialog.relogin();
  </script>
</c:if>
<c:if test="${empty loginJs}">
<link type="image/x-icon" href="http://www.yyfuer.com/images/favicon.ico" rel="shortcut icon">
		
</c:if>
<c:if test="${empty err}">
<style type="text/css" media="screen"> 
            html, body  { height:100%; }
            body { margin:0; padding:0; overflow:auto; text-align:center; 
                   background-color: #060700; }   
        </style>
        <script type="text/javascript">
		var refreshNum = 0;
			function frameRefresh(){
				//alert("fm.index.jsp =========refresh=========");
				if(refreshNum==0){
					//alert("fm.index.jsp not refresh。。。。"+refreshNum);
					refreshNum++;
					
				}else{
					//alert("fm.index.jsp start refresh。。。。"+refreshNum);
					var href = document.location.href.toString();
					checkUrl("loc",href);
					//document.location.reload();				
				}
				
			}
			function checkUrl(paraStr, url)
			{
				var result = "";
				var mappingStr = paraStr+"=1";
				//如果有？
				if(url.indexOf("?")!=-1){
					//如果url中有那个参数
					if(url.indexOf(mappingStr)!=-1){
						//如果前面有&符号
						if(url.indexOf("&"+mappingStr)!=-1){
							result = url.substring(0,url.indexOf("&"+mappingStr))+url.substring(url.indexOf("&"+mappingStr)+("&"+mappingStr).length);
						}else{
							if(url.indexOf(mappingStr+"&")!=-1){
								result = url.substring(0,url.indexOf(mappingStr+"&"))+url.substring(url.indexOf(mappingStr+"&")+(mappingStr+"&").length);
							}else{
								result = url.substring(0,url.indexOf("?"+mappingStr));
							}
						}
					}else{
						result = url+"&"+mappingStr;
					}
				
				}else{//如果没有？，则添加问号和参数
					result = url+"?"+mappingStr;
				}
				document.location.href = result;
			}
			function getRechargeUrl()
			{
				var rechargeUrl = "${rechargeUrl}";
				if(rechargeUrl.indexOf("rechargeUrl")>0){
					rechargeUrl = "";
				}
				return rechargeUrl;
			}
			
        </script>
        <script type="text/javascript">
		function myrefresh() 
		{ 
			xmlHttp = new XMLHttpRequest();
			xmlHttp.open( "GET", "blank.php?ajax=true", false );
			xmlHttp.send(null);
		} 
		setInterval("myrefresh()",25 * 60 * 1000);
		</script>  
<frameset rr="" name="f2" rows="*" frameborder="no" border="0" framespacing="0" id="frameset-one">
<!--     <frame scrolling="no" src="allServers.jsp" id="frame-top"></FRAME> -->
    <frame scrolling="auto"  onload="frameRefresh()" src="http://192.168.5.119:8080/FMClient/bin-debug/main.html?auth=${auth}&url=${rechargeUrl}&loginJs=${loginJs}" id="frameset-game"></frame>
</frameset>
</c:if>
