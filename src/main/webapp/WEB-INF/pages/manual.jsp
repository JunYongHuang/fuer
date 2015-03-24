<%@ include file="/taglibs.jsp"%>

<c:set var="url" value="/handbook/index.htm"></c:set>
<c:if test="${i == -2}">
	<c:set var="url" value="/handbook/index.htm?i=-2"></c:set>
</c:if>
<page:apply-decorator contentType="text/html; charset=utf-8" name="handbook_decorator" page="${url}">
</page:apply-decorator>
