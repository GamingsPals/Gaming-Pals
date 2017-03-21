app.directive("follow",function($compile,auth,$rootScope,ActorService){
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
            $(element).selectric();
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
                            <div class="label bg-green3">A<span class="op">ttitude</span> ${scope.actor.avgattitude}</div>
                        </li>
                        <li class="col">
                            <div class="label bg-red3">S<span class="op">kill</span> ${scope.actor.avgskill}</div>
                        </li>
                        <li class="col">
                            <div class="label bg-magenta3">K<span class="op">nowledge</span> ${scope.actor.avgknowledge}</div>
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
            console.log(scope);
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