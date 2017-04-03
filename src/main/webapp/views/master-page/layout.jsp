
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="icon" href="assets/images/favicon.png" type="image/x-icon" />
    <link rel="shortcut icon" href="assets/images/favicon.png"/>
	<link href="assets/css/style.css" rel="stylesheet"/>
	<link href="assets/css/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="assets/js/selectric/selectric.css" rel="stylesheet" />
    <link href="assets/js/flexslider/flexslider.css" rel="stylesheet"/>
    <script src="assets/js/md5.js"></script>
    <link rel="stylesheet" href="assets/js/angular/ng-dialog/css/ngDialog.min.css">
    <link rel="stylesheet" href="assets/js/angular/ng-dialog/css/ngDialog-theme-default.min.css">
    <link rel="stylesheet" type="text/css" href="assets/js/datatable/datatables.css"/>
    <link rel="stylesheet" type="text/css" href="assets/js/datatable/responsive.dataTables.min.css"/>
	<script src="assets/js/jquery/jquery.js"></script>
	<script src="assets/js/angular/angular.js"></script>
	<script src="assets/js/angular/angular-route.js"></script>
	<script src="assets/js/angular/angular-sanitize.js"></script>
    <script src="assets/js/angular/angular-cookies.min.js"></script>


    <script  src="assets/js/datatable/datatables.min.js"></script>
    <script  src="assets/js/datatable/dataTables.responsive.min.js"></script>

    <script  src="assets/js/datatable/enum.js"></script>
    <script src="assets/js/selectric/jquery.selectric.js"></script>
    <script src="assets/js/app.js"></script>
    <script src="assets/js/flexslider/flexslider.js"></script>
    <script src="assets/js/angular/ng-dialog/js/ngDialog.min.js"></script>

    <title>GamingPals </title>


</head>

<body ng-app="App" ng-controller="MainController">
<a href="#" class="nav-button om"><i class="fa fa-bars"></i> </a>
<nav class="nav-menu" ng-cloak>

    <ul>
        <h2> Menu</h2>
        <li> <a href="home"><i class="fa fa-home"></i> {{loc.home}}</a></li>
        <li> <a href="search"><i class="fa fa-users"></i> {{loc.pals}}</a></li>
        <li ng-show="auth.isAuthenticated()"> <a href="tournament/list"><i class="fa fa-trophy"></i> {{loc.tournaments}}</a></li>
        <li ng-show="auth.isAuthenticated()">  <a href="profile/{{auth.principal.actor.userAccount.username}}"><i class="fa fa-user"></i> {{loc.profile}}</a></li>
        <li  ng-show="!auth.isAuthenticated()"> <a href="#" dialog="login"><i class="fa fa-sign-in"></i> {{loc.login}}</a> </li>
        <li ng-show="!auth.isAuthenticated()">  <a href="signup" ><i class="fa fa-user-plus"></i> {{loc.signup}}</a></li>
        <li ng-show="auth.isAuthenticated()">  <a target="_self" href="j_spring_security_logout" >
            <i class="fa fa-sign-out" aria-hidden="true"></i> {{loc.logout}}
        </a></li>
    </ul>
</nav>

<header class="header" ng-cloak>
    <div class="header-nav">
        <a href="home">
        <img src="assets/images/testlogo.png" />
        </a>
    </div>
    <nav class="nav-horizontal" ng-cloak>
        <ul  ng-show="auth.isAuthenticated()">
            <li> <a href="home"><i class="fa fa-home"></i> {{loc.home}}</a></li>
            <li> <a href="search"><i class="fa fa-users"></i> {{loc.pals}}</a></li>
            <li ng-show="auth.isAuthenticated()"> <a href="tournament/list"><i class="fa fa-trophy"></i> {{loc.tournaments}}</a></li>
            <li> <a href="profile/{{auth.principal.actor.userAccount.username}}" ng-show="auth.isAuthenticated()">
                <i class="fa fa-user"></i> {{loc.profile}}</a></li>
            <li ng-show="auth.hasRole('ADMIN') || auth.hasRole('MODERATOR')">
                <a href="user/reported/list"><i class="fa fa-user-times"></i> User reported list</a> </li>
        </ul>
        <ul class="nav-horizontal-login" >
            <li> <a href="#" ng-show="!auth.isAuthenticated()" dialog="login"><i class="fa fa-sign-in"></i> {{loc.login}}</a> </li>
            <li>  <a href="signup" ng-show="!auth.isAuthenticated()"><i class="fa fa-user-plus"></i> {{loc.signup}}</a></li>
            <li>  <a target="_self" href="j_spring_security_logout" ng-show="auth.isAuthenticated()">
                <i class="fa fa-sign-out" aria-hidden="true"></i> {{loc.logout}}
            </a></li>
        </ul>
    </nav>
</header>

<div class="container" ng-cloak >
    <div class="flexslider">
        <ul class="slides">
            <li class="lolslider">
                <img src="assets/images/games/icons/lolicon.png" class="game-icon" />
                <div class="letter">
                    {{loc.lolslider}}
                </div>
            </li>
            <li class="dotaslider">
                <img src="assets/images/games/icons/dota2icon.png" class="game-icon" />
                <div class="letter">{{loc.dotaslider}}</div>
            </li>
        </ul>
    </div>
    <main ng-view>

    </main>
    <footer>
        <ul>
            <li><a href="about"></a> About us</li></li>
            <li><a href="contact">Contact</a></li>
            <li><a href="legal"> Legal Note</a></li>
            <li><a class="cursor-pointer" href="#" ng-click="loc.changeLan('en')"><flag lang="en"></flag> </a>
                <a  class="cursor-pointer"  href="#" ng-click="loc.changeLan('es')"><flag lang="es"></flag></a>
        </ul>
        <div class="text-center">© 2017-2017 GamingPals.com</div>
    </footer>
</div>
<div class="message-system {{SystemMessages.color}}" ng-show="SystemMessages.show" ng-bind-html="SystemMessages.message">
</div>
<div class="loader"></div>
<script>
    let csrf = {};
    csrf.parameterName = "${_csrf.parameterName}";
    csrf.token  = "${_csrf.token}";
</script>
</body>
</html>