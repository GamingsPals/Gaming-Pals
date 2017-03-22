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
});