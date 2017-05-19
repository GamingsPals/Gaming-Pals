app.directive("adminTools",function($compile,localization,ActorService){
    return {
        restrict: "AEC",
        scope: {
            "adminTools": "="
        },

        link: function(scope,element,attrs){
            scope.$parent.adminTools = scope.adminTools;
            scope.$watch('adminTools',(a)=>{
                if(typeof a!=="undefined"){
                    scope.$parent.adminTools = scope.adminTools;
            let template = ` <div class="dropdown" ng-if="auth.hasRole('ADMIN')" dropdown>
                    <a href="#" class="dropdown-button"><i class="fa fa-gear"></i></a>
                      <ul>
                    <li ng-if="adminTools.userAccount.locked==false">
                    <a href="#" ng-click="AdminService.ban(adminTools)">${localization.admin.bans.ban}</a></li>
                    <li ng-if="adminTools.userAccount.locked==true">
                    <a href="#" ng-click="AdminService.ban(adminTools)">${localization.admin.bans.unban}</a></li>
                        </ul>
                    </div>`;
            $(element).html(template);
            $compile(element.contents())(scope.$parent);
                }
            });
        }
    }



});

app.directive("tournamentTools",function($compile,auth, TournamentService){
    return {
        restrict: "AEC",
        scope: {
            "tournamentTools": "="
        },

        link: function(scope,element,attrs){
            scope.$parent.tournamentTools = scope.tournamentTools;
            scope.$watch('tournamentTools',()=>{
                scope.auth = auth;
                scope.TournamentService = TournamentService;
                console.log(TournamentService.canBeDeleted(scope.tournamentTools));
                let template = ` <div class="dropdown" ng-if="auth.hasRole('ADMIN')" dropdown>
                    <a href="#" class="dropdown-button"><i class="fa fa-gear"></i></a>
                      <ul>
                    <li><a href="#" ng-if="TournamentService.canBeDeleted(tournamentTools)===true" ng-click="TournamentService.delete(tournamentTools)">Delete</a></li>
                        </ul>
                    </div>`;
                $(element).html(template);
                $compile(element.contents())(scope.$parent);
            });
        }
    }



});

app.directive("gamesTools",function($compile,auth,GameInfoService,ActorService){
    return {
        restrict: "AEC",
        scope: {
            "gamesTools": "=",
            "user": "="
        },

        link: function(scope,element,attrs){
            scope.$parent.gamesTools = scope.gamesTools;
            scope.$parent.auth = auth;
            scope.$parent.user = scope.user;
            scope.$parent.GameInfoService = GameInfoService;
            scope.$parent.GameInfoService.addCallbackOnDelete((a)=>{
                ActorService.UserProfile();
            });
            scope.$watch('gamesTools',()=>{
                let template = ` <div class="dropdown" 
                    ng-if="auth.hasRole('USER') && auth.isPrincipal(user)" dropdown>
                    <a href="#" class="dropdown-button"><i class="fa fa-gear"></i></a>
                      <ul>
                    <li><a href="#"
                    ng-click="GameInfoService.delete(gamesTools)">Delete</a></li>
                        </ul>
                    </div>`;
                $(element).html(template);
                $compile(element.contents())(scope.$parent);
            });
        }
    }



});