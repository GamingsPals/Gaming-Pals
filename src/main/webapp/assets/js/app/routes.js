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
				templateUrl: "main",
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
		},
        {
            route : "/profile/:username/:tabs",
            options : {
                templateUrl : "profile",
                controller : "Profile",
            }
        },
		{
			route : "/createTournament",
			options : {
				templateUrl : "createTournament",
				controller : "CreateTournament"
			}
		}, {
			route : "/tournament/list",
			options : {
				templateUrl : "listTournaments",
				controller : "TournamentList"
			}
		},
		{
			route : "/tournament/:id",
			options : {
				templateUrl : "tournament",
				controller : "Tournament"
			}
		},
		{
			route : "/tournament/:id/:menu",
			options : {
				templateUrl : "tournament",
				controller : "Tournament"
			}
		},
		{
			route : "/confrontation/:tournamentId",
			options : {
				templateUrl : "viewConfrontation",
				controller : "ConfrontationTournamentList"
			}
		}

		, {
			route : "/tournament/assign/:tournamentId/:teamId",
			options : {
				templateUrl : "listTournaments",
				controller : "AssignTeamTournament"
			}
		}

		, {
			route : "/award/:tournamentId",
			options : {
				templateUrl : "viewAwards",
				controller : "AwardsTournamentList"
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
				templateUrl : "signup",
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
		},
    {
        route : "/messages",
        options : {
            templateUrl : "main",
            controller : "Message"
        }
    },
    {
        route : "/messages/:userId",
        options : {
            templateUrl : "main",
            controller : "Chat"
        }
    },
	{
        route : "/notifications/:mode",
        options : {
            templateUrl : "notifications",
            controller : "Notifications"
        }
    },

];
