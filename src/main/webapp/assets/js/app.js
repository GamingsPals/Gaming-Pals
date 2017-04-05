let app =
    angular.module('App',['ngRoute','ngSanitize','ngRoute','ngCookies','ngDialog']);


app.run(function($rootScope) {
    $rootScope.$on("$locationChangeStart", function(event, next, current) {
        closeMenu();
    });
});let
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
			route : "/tournament/list",
			options : {
				templateUrl : "listTournaments",
				controller : "TournamentList"
			}
		}, {
			route : "/confrontation/:tournamentId",
			options : {
				templateUrl : "viewConfrontation",
				controller : "ConfrontationTournamentList"
			}
		}

		, {
			route : "/tournament/assign",
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
		}
];
app.config(function($routeProvider,$locationProvider){
    routes.forEach(function(a){
        a.options.templateUrl = `assets/html/${a.options.templateUrl}.html`;
        a.options.controller = `${a.options.controller}Controller`;
        $routeProvider.when(a.route,a.options);
    });

    $routeProvider.otherwise({
        redirectTo: '/'
    });

    $locationProvider.html5Mode(true);
});app.controller('AddSummonerController',function($scope,LolApiService,dialog,ActorService){
    $scope.LolData=LolApiService;
    $scope.test =" Asdad";
    console.log("adsda");
    $scope.validateSummoner = function(){
        console.log($scope.search);
        if (typeof $scope.search.summoner!=="undefined" && typeof $scope.search.region!=="undefined"){
            $scope.check = true;
            $scope.search.key = md5($scope.search.summoner);
            $scope.search.key = $scope.search.key.substring(0, 25);
        }
    };
    $scope.addSummoner = function(){
        $scope.LolData.mainData($scope.search,function(){
            dialog.closeAll();
            $scope.error = false;
            ActorService.UserProfile();
        },function(data){
            console.log(data);
            $scope.error = data.data.message;
        });
    }
});app.controller('AssignTeamTournamentController', function($scope, TournamentService) {
	$scope.As = TournamentService;
	$scope.As.assignTeam($scope.$parent.$parent.tournmanentId, $scope.$parent.$parent.teamId);
});
app.controller('AwardsTournamentListController', function($scope, TournamentService) {
	$scope.As = TournamentService;
	$scope.As.getAwards($scope.$parent.$parent.tournmanentId);
});
app.controller('ConfrontationTournamentListController', function($scope, TournamentService, dialog) {
	$scope.As = TournamentService;
	$scope.As.getConfrontations($scope.$parent.$parent.tournmanentId);

    $scope.reportMatch = function(confrontationId){
        $scope.confrontationId = confrontationId;
        dialog.open("reportMatch",$scope);
    }
});
app.controller('CreateTeamController',function($scope, middleware, ActorService, $routeParams, $rootScope, SystemMessages, dialog){
    $scope.createTeam = function(){
    ActorService.team($scope.teamForm,()=>{});
    $scope.writerating = false;
    $scope.teamForm = null;
    SystemMessages.okmessage("Create team");
    dialog.closeAll();

    }
});app.controller('CreateTournamentController', function($scope, xhr, $location) {
	$scope.enviarTournamentForm = function() {
		xhr.post("api/createTournament", $scope.tournamentform);
		$location.path("/tournament/list");
	}
});

app.controller('HomeController',function($scope){
});
app.controller('LoginController',function(middleware){
    middleware.needRol("NONE");
});

app.controller('LolstatsController',function($scope,MatchService,$routeParams,ActorService){
    $scope.ms = MatchService;
    let id = $routeParams.userid;
    if(typeof $routeParams.userid === "undefined"){
        id = $scope.ActorService.actor.actor.id;
    }
    $scope.ms.getRecentMatches(id);
    $scope.seeMatch = function(matchId){
        console.log(matchId);
    }
});
app.controller('MainController',function($scope, localization, $rootScope, auth, SystemMessages, $sanitize,LoLStaticData){
    localization.init($scope);
    $rootScope.loc = localization;
    $scope.auth = auth;
    $scope.auth.load(function(){});
    $rootScope.csrf = csrf;
    $scope.MessageSystem = SystemMessages;
    $scope.sanitize = $sanitize;
    $scope.lolsd = LoLStaticData;
    $scope.lolsd.loadVersion(()=>{
        $scope.lolsd.loadChampions();
        $scope.lolsd.loadItems();
    });
});app.controller('ProfileController', function($scope, middleware, ActorService, $routeParams, dialog) {
	$scope.ActorService = ActorService;
	$scope.ActorService.UserProfile($routeParams.username);

	$scope.viewUsersByTeam = function(teamId) {
		$scope.teamId = teamId;
		dialog.open("viewUsersByTeam", $scope);
	};

});
app.controller('ReportedUserListController',function($scope,ActorService,middleware,dialog){
    middleware.needRol("ADMIN");
    $scope.As = ActorService;
    $scope.As.reportedUsers();
    $scope.showImage = function(image){
        $scope.image = image;
        dialog.open("showImage",$scope);
    }
});
app.controller('SearchController',function($scope,SearchService,$location){
    $scope.As = SearchService;
    $scope.As.filter($location.search());
});
app.controller('SignupController', function($scope, middleware, xhr, $location) {

	$scope.enviarForm = function() {
		xhr.post("api/signup", $scope.signupform);
		$location.path("/");
	}
});
app.controller('TournamentListController',function($scope,TournamentService, dialog){
    $scope.Ts = TournamentService;
    $scope.Ts.getTournaments();
    $scope.showLongString = function(longString){
        $scope.longString = longString;
        dialog.open("showLongString",$scope);
    };
    $scope.viewAwards = function(tournmanentId){
        $scope.tournmanentId = tournmanentId;
        dialog.open("viewAwards",$scope);
    };
    $scope.viewConfrontation = function(tournmanentId){
        $scope.tournmanentId = tournmanentId;
        dialog.open("viewConfrontation",$scope);
    }
});app.controller('UsersByTeamController', function($scope/* , $routeParams */, ActorService, dialog) {
	$scope.As = ActorService;
	/* $scope.As.getUsers($routeParams.teamId); */
	$scope.As.getUsers($scope.$parent.$parent.teamId);
});
app.controller('WriteRatingController',function($scope, middleware, ActorService, $routeParams, $rootScope, SystemMessages, dialog){
    $scope.rateUser = function(){
        ActorService.rate(ActorService.actor.actor.id,$scope.rateform,()=>{});
        $scope.writerating = false;
        $scope.rateform = null;
        ActorService.UserProfile(ActorService.actor.actor.userAccount.username);
        SystemMessages.okmessage("Rating added");
        dialog.closeAll();
    }
});
app.controller('WriteReportController',function($scope, middleware, ActorService, $routeParams, $rootScope, SystemMessages, dialog){
    $scope.reportUser = function(){
        ActorService.report(ActorService.actor.actor.id,$scope.reportform,()=>{SystemMessages.okmessage("Report send!");
            dialog.closeAll();});

    }
});app.controller('WriteReportMatchController', function($scope, SystemMessages, dialog, TournamentService) {
    $scope.sendReportMatchForm = function() {
        console.log($scope.reportMatchForm);
        console.log($scope.$parent.$parent.confrontationId);
        TournamentService.reportMatch($scope.$parent.$parent.confrontationId,$scope.reportMatchForm,()=>{SystemMessages.okmessage("Report send!");
            dialog.closeAll();});
    }
});
app.service("ActorService",function(xhr,auth){

    this.actor = {};
    this.notFound = false;
    this.search = [];
    this.reportedList = {};
    this.UserProfile = function(name){
        let object = this;
        if(typeof name === "undefined"){
            name = this.actor.actor.userAccount.username;
        }
        xhr.get("api/user/"+name,function(data){
            object.actor = data.data;
            object.processActors();
            object.notFound = false;
        },function(data){
            object.notFound = true;
        });
    };


    this.reportedUsers = function(){
        let object = this;
        xhr.get("api/report/user/list" ,function(response){
            object.reportedList = response.data;
        });
    };




    this.rate = function(user,data,sucess,error){
        let object = this;
        xhr.post("api/user/"+user+"/rate", data,sucess,error);
    };

    this.report = function(user,data,sucess,error){
        let object = this;
        xhr.post("api/user/"+user+"/report", data,sucess,error);
    };

    this.followOrUnfollow = function(id,callback){
        let object = this;
        xhr.get("api/user/"+id+"/follow",function(data){
            if (typeof callback!== "undefined") callback(data);
            auth.load(()=>{},true);
        })
    };
    
    this.team = function(data,sucess,error){
        let object = this;
        xhr.post("team/user/create",data,sucess,error);
    };


    this.processActors = function(){
        let object = this;
        this.actor.actor = this.processActor(this.actor.actor);
        this.actor.followers.forEach(function(a,e){
            object.actor.followers[e] =  object.processActor(a);
        });
        this.actor.following.forEach(function(a,e){
            object.actor.following[e] =  object.processActor(a);
        });
    };

    this.processActor = function(actor){
        actor.avgknowledge = 0;
        actor.avgattitude = 0;
        actor.avgskill = 0;
        let object = this;
        actor.ratingsReceived.forEach(function(a){
            actor.avgknowledge += a.knowledge;
            actor.avgattitude += a.attitude;
            actor.avgskill += a.skill;
        });
        let nRatings = (actor.ratingsReceived.length>0) ? actor.ratingsReceived.length : 1;
        actor.avgknowledge = Math.round((actor.avgknowledge/nRatings) * 100) / 100;
        actor.avgattitude = Math.round((actor.avgattitude/nRatings) * 100) / 100;
        actor.avgskill = Math.round((actor.avgskill/nRatings) * 100) / 100;
        actor.nRatings = actor.ratingsReceived.length;
        actor.avgrating = (actor.avgattitude + actor.avgskill + actor.avgknowledge) / 3;
        return actor;
    };







    this.getUsers = function(teamId){
        let object = this;
        xhr.get("api/users/list?teamId="+teamId ,function(response){
            object.users = response.data;
        });
    };

});
app.service("auth", function(xhr){

    this.principal = {};
    this.load = function(callback,force){
        let object = this;
        if (Object.keys(object.principal).length==0 || force==true) {
            xhr.get("api/isauthenticated", function (data) {
                object.principal = data.data;
                callback();
            })
        }else{
            callback();
        }
    };

    this.isPrincipal = function(actor){
        if (typeof actor === "undefined" || !this.isLoaded() || !this.isAuthenticated()) return false;
        return this.principal.actor.id == actor.id;
    };

    this.isPrincipalFollowing = function(actor){
        if(typeof actor==="undefined" || typeof this.principal.actor==="undefined"){
            return false;
        }
        return typeof this.principal.following.find((a)=> {return a.id == actor.id}) !== "undefined";
    };

    this.isLoaded = function(){
        return !Object.keys(this.principal).length==0;
    };

    this.isAuthenticated = function(){
        if (!this.isLoaded()) return false;
        return this.principal.authenticated;
    };

    this.hasRole = function(rol){
        let result = false;
        if (!this.isLoaded() || !("roles" in this.principal)) return false;
        let rolesAuthority = this.principal.roles;
        rolesAuthority.forEach(function(b){
            if (b.authority.toLowerCase() == rol.toLowerCase()){
                result = true;
            }
        });
        return result;
    }

});
app.service("dialog", function(ngDialog){

    this.open = function(template,scope,controller) {
        let path = "assets/html/"+template+".html";
        let options = {};
        options.template = path;
        if(typeof scope !== "undefined") options.scope = scope;
        if(typeof controller !== "undefined") options.controller = controller;
        ngDialog.open(options);
    };

    this.closeAll = function(){
        ngDialog.closeAll();
    }
});app.service("localization",function(xhr,$cookies){
    this.loc = {};
    this.base_lan = 'en';

    this.init = function(){
        let object = this;
        language = this.getLanguage();
        xhr.get("assets/localitation/"+this.base_lan+".json",function(data){
            Object.assign(object, data.data);
            if (language !== object.base_lan) {
                xhr.get("assets/localitation/" + language + ".json", function (data) {
                    Object.assign(object, data.data);
                });
            }
        });
    };

    this.getLanguage = function(){
        return (typeof $cookies.get("language")!== 'undefined') ? $cookies.get("language") : "en";
    };

    this.changeLan = function(lan){
        console.log("ey");
      $cookies.put("language",lan);
      this.init();
    }
});
app.service("LolApiService",function(xhr){
    this.main = {};

    this.mainData = function(summoner,success,error){
        let object = this;
        xhr.get("api/lol/addsummoner/"+summoner.summoner+"/"+summoner.region+"?key="+summoner.key,function(data){
            if(typeof success !=="undefined")
                success(data);
        },function(data){
            if(typeof error !=="undefined")
                error(data);
        })
    };




});app.service("LoLStaticData",function(xhr){
    this.version = "7.6.1";

    this.loadVersion = function(callback){
        let object = this;
        xhr.get("https://ddragon.leagueoflegends.com/api/versions.json",function(data){
            object.version = data.data[0];
            if (typeof callback!=="undefined"){
                callback();
            }
        })
    };

    this.loadItems = function(){
        let object = this;
        xhr.get(`http://ddragon.leagueoflegends.com/cdn/${this.version}/data/en_US/item.json`, function(data){
            object.items = data.data.data;
        })
    };

    this.getItemIcon = function(item){
        return `//ddragon.leagueoflegends.com/cdn/${this.version}/img/item/${item}.png`;
    };

    this.getProfileIcon = function(icon){
        return `//ddragon.leagueoflegends.com/cdn/${this.version}/img/profileicon/${icon}.png`;
    };

    this.getChampionIcon = function(champion){
        return `//ddragon.leagueoflegends.com/cdn/${this.version}/img/champion/${champion}.png`;
    };

    this.loadChampions = function(){
        let object = this;
        xhr.get(`http://ddragon.leagueoflegends.com/cdn/${this.version}/data/en_US/champion.json`, function(data){
            object.champions = data.data.data;
        })
    };

    this.findChampionById = function(id){
        let champion = null;
        for (let property in this.champions) {
            if (this.champions.hasOwnProperty(property)) {
               if (this.champions[property].key == parseInt(id)){
                   champion = this.champions[property];
               }
            }
        }
        return champion;
    }
});app.service("MatchService",function(xhr,auth){
    this.matches = {};
    this.summoner = {};

    this.getRecentMatches = function(id){
        let object = this;
        xhr.get(`api/lol/stats/${id}`,function(data){
            object.matches = data.data.matches;
            object.summoner = data.data.summoner;
        })
    }

});app.service("middleware",function(auth,$location){

    this.needRol = function(rol){
        console.log("ey");
        let object = this;
        auth.load(function(){
            if (!auth.principal.authenticated) {
                if ( rol.toLowerCase() != "NONE".toLowerCase()) {
                    return object.goTo('login');
                }
                return true;
            }
            if (rol.toLowerCase() == "ANY".toLowerCase()) return true;
            let result = false;
            console.log(rol);
            if (rol.indexOf(',')!="-1"){
                let roles =  rol.split(",");
                console.log(roles);
                roles.forEach(function(a){
                    if ((auth.hasRole(rol))){
                        result = true;
                    }
                });
                console.log(result);
            }
            if ((!auth.hasRole(rol) || rol.toLowerCase() == "NONE".toLowerCase()) && !result){
                return object.goTo('');
            }
        });
    };


    this.goTo = function(path){
        $location.path(path);
        return true;
    }

});

app.service("SearchService", function(xhr){
    this.search = [];

    this.filter = function(filter,callback){
        let object = this;
        xhr.get("api/search?"+$.param(filter),function(data){
            object.search = data.data;
            if (typeof callback !== "undefined") callback();
        })
    };

    this.findAll = function(){
        let object = this;
        xhr.get("api/search",function(data){
            object.search = data.data;
        })
    };
});
app.service("SystemMessages", function($timeout){

    this.color="";
    this.message = "";
    this.show = false;


    this.okmessage = function(message){
        this.color ="bg-green3";
        this.message = `<i class="fa fa-check"></i> ${message}`;
        this.show = true;
        let object = this;
        $timeout(function(){
            object.show = false;
        },2000);
    };

    this.errormessage = function(message){
        this.color ="bg-red3";
        this.message = `<i class="fa fa-close"></i> ${message}`;
        this.show = true;
        let object = this;
        $timeout(function(){
            object.show = false;
        },3000);
    }

});app.service("TournamentService", function(xhr){
    this.tournaments = {};
    this.confrontations = {};
    this.confrontation = {};
    this.getTournaments = function () {
        let object = this;
        xhr.get("api/tournament/list", function (response) {
            object.tournaments = response.data;
        })

    }
    
    this.getConfrontations = function(tournamentId){
        let object = this;
        xhr.get("api/confrontations/tournament/list?tournamentId="+tournamentId ,function(response){
            object.confrontations = response.data;
        });
    };
    
    this.assignTeam = function(tournamentId,teamId){
    	  let object = this;
          xhr.post("api/tournament/assign?tournamentId="+tournamentId+"&teamId="+teamId)
     };
    
    this.getAwards = function(tournamentId){
        let object = this;
        xhr.get("api/awards/tournament/list?tournamentId="+tournamentId ,function(response){
            object.awards = response.data;
        });
    };

    this.reportMatch = function(confrontation,data,sucess,error){
        let object = this;
        xhr.post("api/user/"+confrontation+"/reportMatch", data,sucess,error);
    };
});app.service("xhr",function($http, SystemMessages, $rootScope) {


    this.get = function (url, sucess,error) {
        $(".loader").show();
        $http.get(url).then(function (data) {
                if (typeof sucess !== "undefined") {
                    sucess(data);
                }
                $(".loader").hide();
            },
            function(data) {
                if (typeof error !== "undefined") {
                    error(data);
                }
                $(".loader").hide();
                SystemMessages.errormessage("Something wrong has happened!");
            });
    };

    this.post = function (url, data, sucess,error) {
        $(".loader").show();
        console.log(data);
        data[$rootScope.csrf.parameterName] = $rootScope.csrf.token;
        $http.defaults.headers.post['X-CSRF-TOKEN'] = data._csrf;
        $http({
            method: 'POST',
            url: url,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'X-CSRF-TOKEN': data._csrf

            },
            transformRequest: function (obj) {
                let str = [];
                for (let p in obj) {
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                }
                return str.join("&");
            },
            data: data
        }).then(
            function(data){
                if(typeof sucess !== "undefined"){
                    sucess(data);
                }
                $(".loader").hide();
            },
            function(data) {
                if (typeof error !== "undefined") {
                    error(data);
                }
                SystemMessages.errormessage("Something wrong has happened!");
                $(".loader").hide();
            }
        );
    };
});app.directive("dialog", function(dialog){
    return {
        restrict: "A",
        link: function(scope,element,attrs){
            $(element).on('click',function(e){
                dialog.open(attrs.dialog,scope,attrs.dialogcontroller);
            })
        }
    }
});app.directive("flag", function($compile){
    return {
        restrict: "AE",
        link: function(scope,element,attrs){
            $(element).html(`<img class="flag" ng-src="assets/images/flags/${attrs.lang}.png" /></li>`);
            $compile(element.contents())(scope);
        }
    }
});app.directive("follow",function($compile,auth,$rootScope,ActorService){
    return {
        restrict:"A",
        terminal: true,
        priority: 1000,
        scope:{
            follow: "="
        },
        link: function(scope,element,attrs){
            let changeButton = function(boolean,ele){
                let greyClass = `grey-button`;
                let Class = `button`;
                let followOrFollowing = $rootScope.loc.profileview.following;
                if (boolean){
                    ele.addClass(greyClass);
                    ele.removeClass(Class);
                }else{
                    ele.addClass(Class);
                    ele.removeClass(greyClass);
                    followOrFollowing = $rootScope.loc.profileview.follow;
                }
                ele.html(`${followOrFollowing}`);
            };
            scope.$watch("follow",function(a){
                if (typeof a !== "undefined"){
                    element.attr('is-auth','');
                    element.removeAttr("follow");
                    let following = auth.isPrincipalFollowing(a);
                    changeButton(following,$(element));
                    notPrincipal(element,scope.follow,auth);
                    $(element).on("click",function(e){
                        following = (following != true);
                        changeButton(following,$(this));
                        ActorService.followOrUnfollow(a.id);
                    });
                    $compile($(element))(scope);
                }
            })
        }
    }
});app.directive("giant",function(){
    return {
        restrict: "E",
        link: function(scope,element,attrs){
            $(element).css("font-size","4em");
        }
    }
});app.directive("hasRole", function(auth){
    return{
        restrict: "A",
        link: function(scope,element,attrs){
            if (!auth.isAuthenticated()){
                $(element).hide();
            }
        }
    }

});
app.directive("isAuth", function(auth){
    return{
        restrict: "A",
        link: function(scope,element,attrs){
            if (!auth.isAuthenticated()){
                $(element).hide();
            }
        }
    }

});
app.directive("item",function(LoLStaticData){
    return {
        restrict: "AEC",
        scope:{
            itemid: "="
        },
        link: function(scope,element,attrs){
            scope.$watch(function(d,v){
                $(element).html(`<img src="${LoLStaticData.getItemIcon(scope.itemid)}" width="40" />`);
            })
        }
    }
});
app.directive("loltier",function(){
    return {
        restrict: "AEC",
        scope:{
          loltier: "="
        },
        link: function(scope,element,attrs){
            scope.$watch(function(d,v){
                let tier = 'unranked';
                if(typeof scope.loltier!=="undefined"){
                    tier = scope.loltier.toLocaleLowerCase();
                }
            let assetsPath = `assets/images/games/lol/tiers/`;
            let image = $(`<img class="tier-icon" src="${assetsPath}${tier}.png" />`);
            $(element).html(image);
            })
        }
    }
});app.directive("notPrincipal", function(auth,ActorService){
    return{
        scope: {
            "notPrincipal": "="
        },
        restrict: "A",
        link: function(scope,element,attrs){
            scope.$watch("notPrincipal",function(e){
                notPrincipal(element,scope.notPrincipal,auth);
            });
        }
    }

});

function notPrincipal(element,actor,auth){
    if (auth.isPrincipal(actor)){
        $(element).hide();
    }else{
        $(element).show();
    }
}
app.directive("profileHeader",function(){
    return {
        restrict: "C",
        link: function(scope,element,attrs){
            let random = Math.floor((Math.random() * 7) + 1);
            let url = `url(assets/images/profile-${random}.jpg)`;
            $(element).css("background-image",url);
        }
    }
});
app.directive("rating",function(){
    return{
        restrict: "E",
        scope:{
            actor: "="
        },
        link: function(scope,element,attrs){
            let html = `<ul class="list-horizontal">
                        <li class="col">
                            <div class="label bg-green3" title="Attitude">A ${scope.actor.avgattitude}</div>
                        </li>
                        <li class="col">
                            <div class="label bg-red3" title="Skill">S ${scope.actor.avgskill}</div>
                        </li>
                        <li class="col">
                            <div class="label bg-magenta3" title="Knowledge">K ${scope.actor.avgknowledge}</div>
                        </li>
                    </ul>`;
            $(element).html(html);

        }
    }
});app.directive("report", function(dialog){
    return {
        restrict: "A",
        link: function(scope,element,attrs){
            $(element).addClass("cursor-pointer").addClass("red3");
        }
    }
});
app.directive("select",function(){
    return{
        restrict: "E",
        link: function(scope,element,attrs){
            $(element).selectric(
                {
                    responsive: true,
                    disableOnMobile: true
                }
            );
        }
    }
});String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

$(document).ready(function(){

    $(".nav-button").on("click",function(e){
        e.preventDefault();
      openMenu();
    });
    $("body").on("click",".overlay",function(e){
        e.preventDefault();
        closeMenu();
    });
    $(window).on("scroll",function(){
        changeNavMenuToFixed()
    });

    changeNavMenuToFixed();

    loadSlider();

});

function changeNavMenuToFixed(){
    if ($(window).scrollTop() > 37) {
        $('header').addClass("fixed-menu");
    } else {
        $('header').removeClass("fixed-menu");
    }
}

function openMenu(){
    let overlay = $("<div class='overlay'></div>");
    let menu = $(".nav-menu").children("ul");
    $("body").append(overlay).css("overflow","hidden");
    menu.addClass("open");
}

function closeMenu(){
    $(".nav-menu").children("ul").removeClass("open");
    $("body").css("overflow","auto");
    $(".overlay").remove();
}

function loadSlider(){
    $('.flexslider').flexslider({
        animation: "slide"
    });

}