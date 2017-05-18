app.service("storage",function(){

    this.add = function(scope,name){
        let result = sessionStorage.getItem("name");
        if (!result){
            this.addListener(name,scope);
            return {};
        }

        return result;
    };

    this.addListener = function(name,scope){
      scope.$watch(function(a){
          console.log(a);
          if(typeof a!=="undefined"){
              sessionStorage.setItem(name,scope[name]);
              console.log(sessionStorage.getItem("name"));
          }
      })
    }
});