
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html lang="en">
<head>

	<meta name="google-site-verification" content="KuiLMsxz21j6ykarF10BE83VTARR2IBHWMzU25wD8-4" />
<meta name=”description” content="Be prepared to a hole new gaming experience joining our community. 0% Toxicity. 100% Fun." />


<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="icon" href="assets/images/favicon.png" type="image/x-icon" />
<link rel="shortcut icon" href="assets/images/favicon.png" />
<link href="assets/css/style.min.css" rel="stylesheet" />
<script src="assets/js/md5.js"></script>
<script src="assets/js/app.js"></script>
<title>GamingPals</title>


</head>

<body ng-app="App" ng-controller="MainController">
	<a href="#" class="nav-button om"><i class="fa fa-bars"></i></a>

	<nav class="header" ng-cloak>
		<div class="header-nav">
			<a href="/"> <img src="assets/images/testlogo.png" />
			</a>
		</div>
		<nav class="nav-horizontal" ng-cloak>
			<ul ng-cloak>
				<span ng-if="auth.isAuthenticated()">
					<li><a href="home"><i class="fa fa-home"></i> {{loc.home}}</a></li>
					<li><a href="search"><i class="fa fa-users"></i>
							{{loc.pals}}</a></li>
                    <li><a href="games"><i class="fa fa-gamepad"></i>
							{{loc.games}}</a></li>
					<li><a href="tournaments"><i class="fa fa-trophy"></i>
							{{loc.tournaments}}</a></li>
					<li>
						<form class="plainform" ng-submit="searchUsername(search.name)">
							<i class="fa fa-search cursor-pointer"
								ng-click="searchUsername(search.name)"></i> <input
								class="col align-middle" name="username" ng-model="search.name"
								type="search" placeholder="Search Pal...">
						</form>
				</li>
					</form>
				</span>
				<span ng-if="!auth.isAuthenticated()">
					<li><a href="login"><i class="fa fa-sign-in"></i>
							{{loc.login}}</a></li>
					<li><a href="signup" ng-if="!auth.isAuthenticated()"><i class="fa fa-user-plus"></i> {{loc.signup}}</a></li>
				</span>
                <li ng-if="auth.hasRole('ADMIN') || auth.hasRole('MODERATOR')"><a
                        href="#"
                        ng-if="auth.hasRole('ADMIN')"> <img class="profile-image"
                                                           ng-src="{{auth.principal.actor.picture}}" />
                    {{auth.principal.actor.userAccount.username}}
                </a>
                    <ul>
                        <li >
                            <a href="adminpanel"><i class="fa fa-user-lock"></i>
                                {{loc.layout.userReportedList}}</a>
                        </li>
                        <li><a target="_self" href="j_spring_security_logout"
                              ng-if="auth.isAuthenticated()"> <i class="fa fa-sign-out"
                                                                 aria-hidden="true"></i> {{loc.logout}}
                        </a></li>
                    </ul>
                </li>
				<li ng-if="auth.hasRole('USER')"><a
					href="profile/{{auth.principal.actor.userAccount.username}}"
					> <img class="profile-image"
						ng-src="{{auth.principal.actor.picture}}" />
						{{auth.principal.actor.userAccount.username}}
				</a>
					<ul>
						<li><a ng-if="auth.hasRole('USER')"
							href="profile/{{auth.principal.actor.userAccount.username}}/gameprofiles">
								<i class="fa fa-gamepad" aria-hidden="true"></i>
								{{loc.layout.myGameProfiles}}
						</a></li>
						<li><a ng-if="auth.hasRole('USER')"
							href="profile/{{auth.principal.actor.userAccount.username}}/following">
								<i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i>
								{{loc.layout.myFollowing}}
						</a></li>
						<li><a ng-if="auth.hasRole('USER')"
							href="profile/{{auth.principal.actor.userAccount.username}}/followers">
								<i class="fa fa-arrow-circle-o-left" aria-hidden="true"></i>
								{{loc.layout.myFollowers}}
						</a></li>
						<li><a ng-if="auth.hasRole('USER')"
							href="profile/{{auth.principal.actor.userAccount.username}}/teams">
								<i class="fa fa-shield" aria-hidden="true"></i>
								{{loc.layout.myTeams}}
						</a></li>
						<li><a ng-if="auth.hasRole('USER')"
							href="profile/{{auth.principal.actor.userAccount.username}}/ratings">
								<i class="fa fa-star" aria-hidden="true"></i>
								{{loc.layout.ratingsReceived}}
						</a></li>
						<li><a target="_self" href="j_spring_security_logout"
							ng-if="auth.isAuthenticated()"> <i class="fa fa-sign-out"
								aria-hidden="true"></i> {{loc.logout}}
						</a></li>
					</ul></li>
				<li><a href="#" class="relative" ng-if="auth.hasRole('USER')">
						<div class="notification-globe"
							ng-show="notifications.numberOfNotifications()>0">
							{{notifications.numberOfNotifications()}}</div>  <i
							class="fa fa-bell"></i>{{loc.layout.notifications}}
					</span>
				</a>
					<ul>
						<li ng-if="auth.hasRole('USER')"><a href="notifications/teaminvitations"><i class="fa fa-shield"></i>
								{{loc.layout.teamNotifications}}</a>
							<div class="notification-globe float-right"
								ng-show="notifications.notifications.TeamInvitations.length>0">
								{{notifications.notifications.TeamInvitations.length}}</div></li>
						<li ng-if="auth.hasRole('USER')"><a
							href="notifications/followers">
							<i class="fa fa-arrow-circle-o-left"></i>
								{{loc.layout.newFollowers}}</a>
							<div class="notification-globe float-right"
								ng-show="notifications.notifications.Follower.length>0">
								{{notifications.notifications.Follower.length}}</div></li>
					</ul></li>
				<li><a href="messages" class="relative"
					ng-if="auth.isAuthenticated()">
						<i class="fa fa-envelope"></i>
							{{loc.layout.messages}}
						<div class="notification-globe"
							ng-show="notifications.getNumNewMessages()>0">
							{{notifications.getNumNewMessages()}}</div>
				</a></li>
			</ul>
		</nav>
	</nav>

	<div class="container" ng-cloak>

		<main ng-view> </main>
		<footer>
			<ul>
				<li><a href="about" ng-click="aboutUs()"> {{loc.layout.aboutUs}}</a> </li>
				<li><a href="contact">{{loc.layout.contact}}</a></li>
				<li><a href="legal">
						{{loc.layout.legalNote}}</a></li>
				<li><a class="cursor-pointer" href="#"
					ng-click="changeLan('en')"><flag lang="en"></flag> </a> <a
					class="cursor-pointer" href="#" ng-click="changeLan('es')"><flag
							lang="es"></flag></a></li>
			</ul>
			<div class="text-center">© 2017 Gaming-Pals.com</div>
		</footer>
	</div>
	<div class="message-system {{SystemMessages.color}}"
		ng-show="SystemMessages.show" ng-bind-html="SystemMessages.message">
	</div>
	<div ng-if="auth.isAuthenticated()" class="feedbackButton">
		<a href="feedback">Feedback</a>
	</div>

	<div class="beta"></div>
	<script>
		let
		csrf = {};
		csrf.parameterName = "${_csrf.parameterName}";
		csrf.token = "${_csrf.token}";
	</script>
</body>
</html>