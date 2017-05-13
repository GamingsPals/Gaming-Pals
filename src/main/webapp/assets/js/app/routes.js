let
routes = [
		{
			route : "/",
			options : {
				templateUrl : "main",
				controller : "Home",
			}
		}, {
			route : "/updatepaypal",
			options : {
				templateUrl : "user/updatepaypal",
				controller : "Paypal",
			}
		}, {
			route : "/login",
			options : {
				templateUrl : "main",
				controller : "Login",
			}
		}, {
			route : "/passwordRecovery",
			options : {
				templateUrl : "main",
				controller : "PasswordRecovery",
			}
		}, {
			route : "/adminpanel",
			options : {
				templateUrl : "admin/panel",
				controller : "AdminPanel",
			}
		}, {
			route : "/adminpanel/:menu",
			options : {
				templateUrl : "admin/panel",
				controller : "AdminPanel",
			}
		}, {
			route : "/profile/:username",
			options : {
				templateUrl : "profile/profile",
				controller : "Profile",
			}
		}, {
			route : "/profile/:username/:tabs",
			options : {
				templateUrl : "profile/profile",
				controller : "Profile",
			}
		}, {
			route : "/createTournament",
			options : {
				templateUrl : "tournaments/createTournament",
				controller : "CreateTournament"
			}
		}, {
			route : "/tournament/list",
			options : {
				templateUrl : "tournaments/listTournaments",
				controller : "TournamentList"
			}
		}, {
			route : "/tournament/:id",
			options : {
				templateUrl : "tournaments/tournament",
				controller : "Tournament"
			}
		}, {
			route : "/team/:name",
			options : {
				templateUrl : "team",
				controller : "Team"
			}
		}, {
			route : "/tournament/:id/:menu",
			options : {
				templateUrl : "tournaments/tournament",
				controller : "Tournament"
			}
		},

		{
			route : "/search",
			options : {
				templateUrl : "search",
				controller : "Search",
			}
		}, {
			route : "/signup",
			options : {
				templateUrl : "main",
				controller : "Signup"
			}
		}, {
			route : "/lol/stats/:userid",
			options : {
				templateUrl : "lolstats",
				controller : "Lolstats"
			}
		}, {
			route : "/team/users/:teamId",
			options : {
				templateUrl : "viewUsersByTeam",
				controller : "UsersByTeam"
			}
		}, {
			route : "/messages",
			options : {
				templateUrl : "main",
				controller : "Message"
			}
		}, {
			route : "/contact",
			options : {
				templateUrl : "contact",
				controller : "Contact"
			}
		}, {
			route : "/messages/:userId",
			options : {
				templateUrl : "main",
				controller : "Chat"
			}
		}, {
			route : "/notifications/:mode",
			options : {
				templateUrl : "notifications",
				controller : "Notifications"
			}
		},

];
