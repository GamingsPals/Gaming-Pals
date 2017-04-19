app.service("LanguageService", function(xhr){

    this.getAll = function(callback){
             xhr.get("api/languages/all", function(response){
            console.log(response);
            if(typeof  callback!=="undefined"){
                callback(response.data);
            }
        })
    }
});