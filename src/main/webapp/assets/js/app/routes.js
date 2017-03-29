let routes =
    [
        {
            route: "/",
            options: {
                templateUrl: "main",
                controller: "Home",
            }
        },
        {
            route: "/login",
            options: {
                templateUrl: "login",
                controller: "Login",
            }
        },
        {
            route: "/user/reported/list",
            options: {
                templateUrl: "viewreport",
                controller: "ReportedUserList",
            }
        },
        {
            route: "/profile/:username",
            options: {
                templateUrl: "profile",
                controller: "Profile",
            }
        },
        {
            route: "/search",
            options: {
                templateUrl: "search",
                controller: "Search",
            }
        },
        {
            route: "/signup",
            options:{
                templateUrl: "signup",
                controller: "Signup"
            }

        }
    ];

