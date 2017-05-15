app.controller('MessageController',function($scope, socket,chat,auth,ActorService,middleware, dialog){
    middleware.needRol("USER,MODERATOR,ADMIN");
    $scope.As = ActorService;
    $scope.soc = socket;
    $scope.chat = chat;
    $scope.showMessages = false;
    $scope.showAutoComplete = false;
    chat.userselected = undefined;
    $scope.auth = auth;
    $scope.chat.getRecents((a)=>{
        console.log(chat.recents);
    });
    ActorService.findAll(function(data){
        $scope.all = data;
    });
    $scope.disconnect = function(){
        $scope.soc.disconnect();
    };

    $scope.notPrincipal = function(x){
        return x.id!= auth.principal.actor.id;
    };
    let interval = false;

    let dialog2 = dialog.open("includes/messages",$scope);

    chat.scopes.push($scope);
    dialog.redirect(dialog2,()=>{
        dialog.closeAll();
        chat.scopes.splice(chat.scopes.indexOf($scope),1);
    });

});