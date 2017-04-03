app.controller('CreateTeamController',function($scope, middleware, ActorService, $routeParams, $rootScope, SystemMessages, dialog){
    $scope.createTeam = function(){
    ActorService.team($scope.teamForm,()=>{});
    $scope.writerating = false;
    $scope.teamForm = null;
    SystemMessages.okmessage("Create team");
    dialog.closeAll();

    }
});