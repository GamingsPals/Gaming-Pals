<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="toxic/administrator/edit.do" modelAttribute="toxic">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="keywords">
		<spring:message code="toxic.keywords" />:
	</form:label>
	<form:input path="keywords" />
	<form:errors cssClass="error" path="keywords" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="toxic.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
		value="<spring:message code="toxic.cancel" />"
		onclick="javascript: relativeRedir('toxic/administrator/list.do');" />
	<br />

</form:form>