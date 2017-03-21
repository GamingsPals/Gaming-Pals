<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form method="post">

    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <label for="name">
        Name
    </label>
    <input name="name" id="name" type="text" placeholder="name"/>
    <label for="region">
       Region
    </label>

    <select name="region" id="region">
        <option value="euw">euw</option>
    </select>

    <input type="submit" value="send"/>
</form>

<%--@elvariable id="summoner" type="domain.Summoner"--%>
<jstl:if test="${not empty summoner}">

    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>ID</th>
            <th>Name</th>
            <th>Tier</th>
            <th>Division</th>
            <th>Points</th>
            <td>Matches</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><img src="http://ddragon.leagueoflegends.com/cdn/7.5.2/img/profileicon/${summoner.profileIconId}.png"
                     width="50" /></td>
            <td>${summoner.idSummoner}</td>
            <td>${summoner.username}</td>
            <td>${summoner.leagues[0].tier}</td>
            <td>${summoner.leagues[0].division}</td>
            <td>${summoner.leagues[0].points}</td>
            <td><a href="lolapitest/matchs.do?summonerId=${summoner.idSummoner }">Matchs</a></td>
        </tr>
        </tbody>
    </table>
</jstl:if>