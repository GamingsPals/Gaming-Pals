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
});