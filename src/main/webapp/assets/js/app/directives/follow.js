app.directive("follow",function($compile,auth,$rootScope,ActorService){
    return {
        restrict:"A",
        scope:{
            follow: "="
        },
        link: function(scope,element,attrs){
            scope.$watch("follow",(a)=>{
            scope.ActorService = ActorService;
                    scope.isFollowingText = function(a){
                        let result = $rootScope.loc.profileview.follow;
                        if (auth.isPrincipalFollowing(a)){
                            result =  $rootScope.loc.profileview.following;
                        }
                        return result;
                    };

                    scope.isFollowing = function(){
                        let result = false;
                        if (auth.isPrincipalFollowing(a)){
                            result =  true;
                        }
                        return result;
                    };
                    scope.followOrUnfollow = function(a){
                        ActorService.followOrUnfollow(a.id,(b)=>{
                            ActorService.UserProfile(a.userAccount.username);
                        })
                    };
                    scope.auth = auth;
                    let template =
                        `<div ng-if="!auth.isPrincipal(follow) && auth.hasRole('USER')" ng-class="(isFollowing(follow)) ? 'grey-button' : 'button'" 
                            ng-click="followOrUnfollow(follow)">{{isFollowingText(follow)}}</div>`;
                    $(element).html(template);
                    $compile(element.contents())(scope);
            })
                }

    }
});