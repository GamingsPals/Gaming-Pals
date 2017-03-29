<%--
 * index.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<spring:message code="message.sure" var="sure"/>

<script>
	function myFunction(id,t) {
	   
	    var r = confirm(t);
	    if (r == true) {
	    	document.location.href='message/actor/delete?messageId='+id;
	    } else {
	        
	    }
	    
	}
	</script>

<b><spring:message code="message.received"/></b><br>
<display:table name="received" id="row" requestURI="${requestUri}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	<spring:message code="message.receiver" var="receiverColumn"/>
	<display:column title="${receiverColumn}">
			<jstl:out value="${row.receiver.name}" />
	</display:column>
	
	<spring:message code="message.sender" var="senderColumn"/>
	<display:column title="${senderColumn}">
			<jstl:out value="${row.sender.name}" />
	</display:column>
	
	<spring:message code="message.title" var="messageColumn"/>
	<display:column property="title" title="${messageColumn}"/>
	
	
	<spring:message code="message.text" var="textColumn"/>
	<display:column property="text" title="${textColumn}"/>
	
	<spring:message code="message.moment" var="momentColumn"/>
	<display:column property="moment" title="${momentColumn}"/>
	
	
	<spring:message code="message.delete" var="deleteColumn"/>
	<display:column title="${deleteColumn}">
	
	<input type="button" onclick="myFunction(${row.id},'${sure}')" value="${deleteColumn }"/>
	
	</display:column>
	
	
	<spring:message code="message.reply" var="replyColumn"/>
	<display:column title="${replyColumn}">
	<input type="button" onclick="document.location.href='message/actor/reply?senderName=${row.sender.name}'" value="${replyColumn }"/>
	</display:column> 
	
	<spring:message code="message.forward" var="forwardColumn"/>
	<display:column title="${forwardColumn}">
	<input type="button" onclick="document.location.href='message/actor/forward?messageId=${row.id}'" value="${forwardColumn }"/>
	</display:column>
	
	
	
</display:table>

<br><br>
<b><spring:message code="message.sended"/></b><br>
<display:table name="sended" id="row" requestURI="${requestUri}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	<spring:message code="message.receiver" var="receiverColumn"/>
	<display:column title="${receiverColumn}">
			<jstl:out value="${row.receiver.name}" />
	</display:column>
	
	<spring:message code="message.sender" var="senderColumn"/>
	<display:column title="${senderColumn}">
			<jstl:out value="${row.sender.name}" />
	</display:column>
	
	<spring:message code="message.title" var="messageColumn"/>
	<display:column property="title" title="${messageColumn}"/>
	
	
	<spring:message code="message.text" var="textColumn"/>
	<display:column property="text" title="${textColumn}"/>
	
	<spring:message code="message.moment" var="momentColumn"/>
	<display:column property="moment" title="${momentColumn}"/>
	
	<spring:message code="message.delete" var="deleteColumn"/>
	<display:column title="${deleteColumn}">

	<input type="button" onclick="myFunction(${row.id},'${sure}')" value="${deleteColumn }"/>
	</display:column>
	
	<spring:message code="message.forward" var="forwardColumn"/>
	<display:column title="${forwardColumn}">
	<input type="button" onclick="document.location.href='message/actor/forward?messageId=${row.id}'" value="${forwardColumn }"/>
	</display:column>
	
</display:table>


