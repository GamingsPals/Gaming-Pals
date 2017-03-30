
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="toxics" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
	
	<spring:message var="keywords" code="toxic.keywords"/>
	<display:column title="${keywords}">
		<jstl:forEach var="keyword" items="${row.keywords}">
			<tr>
				<td>
					<jstl:out value="${keyword}"></jstl:out>
					<a href="toxic/administrator/edit.do?toxicId=${row.id}&keyword=${keyword}"> <spring:message
				code="toxic.delete" />
			</a>
				</td>			
			</tr>
		</jstl:forEach>
	</display:column>
	
</display:table>

<script type = "text/javascript">
	function addSpam(){
		var keyword = document.getElementById("keyword").value;
		window.location.replace('toxic/administrator/addSpam.do?keyword='+keyword);
	}
</script>

<div>
    <input type="text" name="keyword" id = "keyword">  
	<input type="button" name="addSpam"
		value="<spring:message code="toxic.addSpam" />"
		onclick="javascript: addSpam();"/>
	
		
</div>

<%-- <security:authorize access="hasRole('ADMIN')">
			<a href="toxic/administrator/edit.do?toxicId=${row.id}"> <spring:message
				code="toxic.edit" />
			</a>
</security:authorize>  --%>
