let app =
    angular.module('App',['ngRoute','ngSanitize','ngRoute','ngCookies','ngDialog'])
        .config(function($routeProvider,$locationProvider){
            $routeProvider.when("/", {
                templateUrl : "assets/html/main.html",
                controller: "HomeController"
            })
                .when("/login", {
                    templateUrl : "assets/html/login.html",
                    controller: "LoginController"
                })
                .when("/profile/:username", {
                    templateUrl : "assets/html/profile.html",
                    controller: "ProfileController"
                }).when("/search", {
                templateUrl : "assets/html/search.html",
                controller: "SearchController"
            })
                .otherwise({
                    redirectTo: '/'

                });
            $locationProvider.html5Mode(true);
        });


;app.controller('MainController',function($scope,localization,$rootScope,auth,MainPageService,MessageSystem,$sanitize){
    localization.init($scope);
    $rootScope.loc = localization;
    $scope.auth = auth;
    $scope.auth.load(function(){});
    $rootScope.csrf = csrf;
    $scope.MainPageService = MainPageService;
    $scope.MainPageService.mainData();
    $scope.MessageSystem = MessageSystem;
    $scope.sanitize = $sanitize;
});

app.controller('HomeController',function($scope){
});

app.controller('SearchController',function($scope,ActorService){
    $scope.As = ActorService;
    $scope.As.findAll();
});

app.controller('LoginController',function(middleware){
    middleware.needRol("NONE");
});

app.controller('ProfileController',function($scope,middleware,ActorService,$routeParams){
    $scope.ActorService = ActorService;
    $scope.ActorService.UserProfile($routeParams.username);
});

app.controller('WriteRatingController',function($scope,middleware,ActorService,$routeParams,$rootScope,MessageSystem,dialog){
    $scope.rateUser = function(){
        ActorService.rate(ActorService.actor.actor.id,$scope.rateform,()=>{});
        $scope.writerating = false;
        $scope.rateform = null;
        ActorService.UserProfile(ActorService.actor.actor.userAccount.username);
        MessageSystem.okmessage("Rating added");
        dialog.closeAll();
    }
});


app.controller('WriteReportController',function($scope,middleware,ActorService,$routeParams,$rootScope,MessageSystem,dialog){
    $scope.reportUser = function(){
        ActorService.report(ActorService.actor.actor.id,$scope.reportform,()=>{MessageSystem.okmessage("Report send!");
            dialog.closeAll();});

    }
});;
app.service("xhr",function($http,MessageSystem,$rootScope) {


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
                MessageSystem.errormessage("Something wrong has happened!");
            });
    };

    this.post = function (url, data, sucess,error) {
        $(".loader").show();
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
                MessageSystem.errormessage("Something wrong has happened!");
                $(".loader").hide();
            }
        );
    };
});

app.service("localization",function(xhr,$cookies){
    this.loc = {};
    this.base_lan = 'en';

    this.init = function($scope){
        let object = this;
        language = this.getLanguage($cookies);

        xhr.get("assets/localitation/"+this.base_lan+".json",function(data){
            Object.assign(object, data.data);
            if (language != object.base_lan) {
                xhr.get("assets/localitation/" + language + ".json", function (data) {
                    Object.assign(object, data.data);
                });
            }
        });
    };

    this.getLanguage = function(cookies){
        return (typeof cookies.get("language")!= 'undefined') ? cookies.get("language") : "en";
    }
});


app.service("MainPageService",function(xhr,ActorService){
    this.main = {};

    this.mainData = function(){
        let object = this;
        xhr.get("api/main",function(data){
            object.main = data.data;
            data.data.bestclassified = object.processActors(data.data.bestclassified);
        })
    };

    this.processActors = function (actors) {
        actors.forEach(function(a,e){
            actors[e] = ActorService.processActor(a);
        });

        return actors;
    }

});

app.service("ActorService",function(xhr,auth){

    this.actor = {};
    this.notFound = false;
    this.search = [];

    this.UserProfile = function(name){
        let object = this;
        xhr.get("api/user/"+name,function(data){
            object.actor = data.data;
            object.processActors();
            object.notFound = false;
        },function(data){
            object.notFound = true;
        })
    };


    this.findAll = function(){
      let object = this;
        xhr.get("api/search",function(data){
            object.search = data.data;
        })
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

    this.processActors = function(){
        let object = this;
        this.actor.actor = this.processActor(this.actor.actor);
        this.actor.followers.forEach(function(a,e){
            object.actor.followers[e] =  object.processActor(a);
        });
        this.actor.following.forEach(function(a,e){
            object.actor.following[e] =  object.processActor(a);
        });
        if (this.actor.actor.ratingsReceived != null){
            this.actor.actor.ratingsReceived.forEach(function(a,e){
                object.actor.actor.ratingsReceived[e].ratingUser =  object.processActor(a.ratingUser);
            });
        }
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
        let rolesAuthority = this.principal.roles;
        rolesAuthority.forEach(function(b){
            if (b.authority.toLowerCase() == rol.toLowerCase()){
                result = true;
            }
        });
        return result;
    }

});

app.service("middleware",function(auth,$location){

    this.needRol = function(rol){
        let object = this;
        auth.load(function(){
            if (!auth.principal.authenticated) {
                if ( rol.toLowerCase() != "NONE".toLowerCase()) {
                    return object.goTo('login');
                }
                return true;
            }
            if (rol.toLowerCase() == "ANY".toLowerCase()) return true;
            if ((!auth.hasRole(rol) || rol.toLowerCase() == "NONE".toLowerCase())){
                return object.goTo('');
            }
        });
    };


    this.goTo = function(path){
        $location.path(path);
        return true;
    }

});


app.service("MessageSystem", function($timeout){

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
});;;app.directive("follow",function($compile,auth,$rootScope,ActorService){
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

app.directive("notPrincipal", function(auth,ActorService){
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

app.directive("report", function(dialog){
    return {
        restrict: "A",
        link: function(scope,element,attrs){
            $(element).addClass("cursor-pointer").addClass("red3");
        }
    }
});

app.directive("dialog", function(dialog){
    return {
        restrict: "A",
        link: function(scope,element,attrs){
            $(element).on('click',function(e){
                dialog.open(attrs.dialog,scope,attrs.dialogcontroller);
            })
        }
    }
});

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

app.directive("giant",function(){
    return {
        restrict: "E",
        link: function(scope,element,attrs){
            $(element).css("font-size","4em");
        }
    }
});;

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
        $('.nav-horizontal').addClass("fixed-hor-menu");
    } else {
        $('header').removeClass("fixed-menu");
        $('.nav-horizontal').removeClass("fixed-hor-menu");
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