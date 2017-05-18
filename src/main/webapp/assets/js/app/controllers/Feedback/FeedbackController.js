app.controller("FeedbackController",function($scope,FeedbackService,middleware,auth){
    middleware.needRol('ANY');
    $scope.FeedbackService = FeedbackService;
    FeedbackService.all();
    $scope.error = [];
    $scope.submitFeedback = function(string,i,parent){
       FeedbackService.add(string.body,parent,(a)=>{
           FeedbackService.all();
           $scope.error[i] = null;
           string.body = null;
           $scope.feedbackForm = {};
       },(a)=>{
           $scope.error[i] = a.data.message;
       })
    };

    $scope.getLikedClass = function(item,liked){
      let result = '';
      let user = auth.principal.actor;
      item.likes.forEach((a)=>{
          if(a.author.id=== user.id){
              if(a.liked===liked){
                  result = 'liked';
              }else{
                  result = 'disliked';
              }
          }

      });
        return result;
    };
    $scope.dislike = function(item){
        FeedbackService.dislike(item,(a)=>{
            FeedbackService._all = a.data;
        });
    };

    $scope.like = function(item){
        FeedbackService.like(item,(a)=>{
            FeedbackService._all = a.data;
        });
    };

    $scope.getOpacity = function(item){
        let puntuation = $scope.getLikePuntuation(item);
        let result = 1;
        if(puntuation>=0 || item.likes.length===0) return 1;
        let ratioNegative = Math.abs(puntuation/item.likes.length);
        result = (5 - ratioNegative*item.likes.length)/5;
        console.log(ratioNegative);

        return result;
    };

    $scope.getLikePuntuation =  function (value){
        let likes = value.likes;
        let puntuacion = 0;
        likes.forEach((a)=>{
            if(a.liked){
                puntuacion = puntuacion+1;
            }else{
                puntuacion = puntuacion-1;
            }
        });
        return puntuacion;
    };
});