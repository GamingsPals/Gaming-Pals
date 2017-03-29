app.service("localization",function(xhr,$cookies){
    this.loc = {};
    this.base_lan = 'en';

    this.init = function(){
        let object = this;
        language = this.getLanguage();
        xhr.get("assets/localitation/"+this.base_lan+".json",function(data){
            Object.assign(object, data.data);
            if (language !== object.base_lan) {
                xhr.get("assets/localitation/" + language + ".json", function (data) {
                    Object.assign(object, data.data);
                });
            }
        });
    };

    this.getLanguage = function(){
        return (typeof $cookies.get("language")!== 'undefined') ? $cookies.get("language") : "en";
    };

    this.changeLan = function(lan){
        console.log("ey");
      $cookies.put("language",lan);
      this.init();
    }
});
