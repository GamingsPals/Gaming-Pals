<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<%--@elvariable id="summoner" type="services.apis.lol.Entity.Summoner"--%>
<jstl:if test="${not empty matchs}">

    <table>
        <thead>
        <tr>
            <th>MatchID</th>
            <th>Date</th>
            <th>Champion</th>
            <th>Result</th>
            <th>Score</th>
            <th>Items</th>
            <th>Team(SummonerId)</th>
        </tr>
        </thead>
        <tbody>
         <jstl:forEach items="${matchs}" var="match">
        <tr>
            <td>${match.id}</td>
            <td>${match.createdGame}</td>
            <td><img src="${match.championId.urlImage}" width="50"/></td>
            <td><jstl:if test="${match.isWin==true}">
            	<i class="fa fa-check" style="color:green;" aria-hidden="true"></i>
            </jstl:if>
            
            <jstl:if test="${match.isWin==false}">
            	<i class="fa fa-times" style="color:red;" aria-hidden="true"></i>
            </jstl:if></td>
            <td>${match.kills}/${match.deaths}/${match.assists}</td>
            <td>
            <jstl:forEach items="${match.itemsGame}" var="itemGame">
            	<img src="${itemGame.urlImage}" width="50"/>
            </jstl:forEach>
            </td>
            <td>${match.summonerIdTeam}</td>
        </tr>
        </jstl:forEach>
        </tbody>
    </table>
</jstl:if>