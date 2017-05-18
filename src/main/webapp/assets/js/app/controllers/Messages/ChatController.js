app.controller('ChatController',function($scope, socket,chat,auth,ActorService,middleware, dialog, $routeParams,
                                         $location,NotificationService){
    middleware.needRol("USER,MODERATOR,ADMIN");
    $scope.As = ActorService;
    $scope.soc = socket;
    $scope.chat = chat;
    $scope.showMessages = false;
    $scope.auth = auth;

    $scope.disconnect = function(){
        $scope.soc.disconnect();
    };

    $scope.notPrincipal = function(x){
        return x.id!= auth.principal.actor.id;
    };
    let user = $routeParams.userId;
    if(typeof user!=="undefined"){
        $scope.As.getOneActor(user,(data)=>{
            $scope.chat.userselected = data;
            chat.createMessagesIfNotExists(data.id);
            NotificationService.setReaded(data);
            chat.getMessages();
        })
    }

    let interval = false;

    $scope.enterMessage = function(){
        if($scope.inputmessage!=null && typeof $scope.inputmessage!=="undefined" && $scope.inputmessage!=''){
            chat.sendMessage($scope.inputmessage);
            $scope.inputmessage='';
            $(".chat-body-chat-inputbox-input").text("");
        }
    };

    $scope.sendMessage = function($event){
        interval = true;
        $scope.inputmessage = $(".chat-body-chat-inputbox-input").text();
        chat.setTyping(true);
        if ($event.key=='Enter'){
            $event.preventDefault();
            $scope.enterMessage();
        }
    };

    $scope.updateTyping = function(){
        interval = false;
        setTimeout(function(){
            if (interval===false){
                chat.setTyping(false);
                interval = true;
            }
        },500);
    };

    let dialog2 = dialog.open("includes/chat",$scope);
    chat.scopes.push($scope);

    dialog.redirect(dialog2,()=>{
        $scope.chat.userselected = null;
        chat.scopes.splice(chat.scopes.indexOf($scope),1);
    });

    $scope.goMessages = function(){
        dialog.close(dialog2);
        $location.path("messages");
    }

});