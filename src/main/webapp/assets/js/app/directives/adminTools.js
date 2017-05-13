app.directive("adminTools",function($compile){
    return {
        restrict: "AEC",
        scope: {
            "adminTools": "="
        },

        link: function(scope,element,attrs){
            scope.$parent.adminTools = scope.adminTools;
            scope.$watch('adminTools',()=>{
            let template = ` <div class="dropdown" ng-if="auth.hasRole('ADMIN')" dropdown>
                    <a href="#" class="dropdown-button"><i class="fa fa-gear"></i></a>
                      <ul>
                    <li ng-if="adminTools.userAccount.locked==false"><a href="#" ng-click="AdminService.ban(adminTools)">Ban User</a></li>
                    <li ng-if="adminTools.userAccount.locked==true"><a href="#" ng-click="AdminService.ban(adminTools)">Unban User</a></li>
                        </ul>
                    </div>`;
            $(element).html(template);
            $compile(element.contents())(scope.$parent);
            });
        }
    }



});

app.directive("tournamentTools",function($compile){
    return {
        restrict: "AEC",
        scope: {
            "tournamentTools": "="
        },

        link: function(scope,element,attrs){
            scope.$parent.tournamentTools = scope.tournamentTools;
            scope.$watch('tournamentTools',()=>{
                let template = ` <div class="dropdown" ng-if="auth.hasRole('ADMIN')" dropdown>
                    <a href="#" class="dropdown-button"><i class="fa fa-gear"></i></a>
                      <ul>
                    <li><a href="#" ng-if="TournamentService.canBeDeleted(tournamentTools)" ng-click="TournamentService.delete(tournamentTools)">Delete</a></li>
                        </ul>
                    </div>`;
                $(element).html(template);
                $compile(element.contents())(scope.$parent);
            });
        }
    }



});