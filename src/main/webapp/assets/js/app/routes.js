let
routes = [
		{
			route : "/",
			options : {
				templateUrl : "main",
				controller : "Home",
			}
		}, {
			route : "/login",
			options : {
				templateUrl : "login",
				controller : "Login",
			}
		}, {
			route : "/user/reported/list",
			options : {
				templateUrl : "viewreport",
				controller : "ReportedUserList",
			}
		}, {
			route : "/profile/:username",
			options : {
				templateUrl : "profile",
				controller : "Profile",
			}
		}, {
			route : "/createTournament",
			options : {
				templateUrl : "createTournament",
				controller : "CreateTournament"
			}
		}, {
			route : "/search",
			options : {
				templateUrl : "search",
				controller : "Search",
			}
		}, {
			route : "/signup",
			options : {
				templateUrl : "signup",
				controller : "Signup"
			}
		}, {
			route : "/lol/stats/:userid",
			options : {
				templateUrl : "lolstats",
				controller : "Lolstats"
			}
		}
];
