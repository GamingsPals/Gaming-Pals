<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="messageForm">
	
	<jstl:if test="${forward==true}">
	<acme:textbox  readonly="true" code="message.title" path="title"/>
	<acme:textarea readonly="true" code="message.text" path="text"/>
	</jstl:if>
	
	<jstl:if test="${forward==false}">
	<acme:textbox  code="message.title" path="title"/>
	<acme:textarea code="message.text" path="text"/>
	</jstl:if>
	
	
	<jstl:if test="${reply == false}">
	<acme:select items="${users}" itemLabel="userAccount.username" code="message.receiver" path="receiver" />
	</jstl:if>
	
	<jstl:if test="${reply == true}">
	<form:hidden path="receiver"/>
	<form:label path="receiver">
		<spring:message code="message.receiver" />
	</form:label>	
	<form:input disabled="true" path="receiver.userAccount.username" readonly="true" />	<br>
	</jstl:if>
	
	<acme:submit code="message.save" name="save"/>
	
	<acme:cancel code="comment.cancel" url="/message/actor/list.do"/>
	
</form:form>