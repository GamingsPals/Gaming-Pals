app.service("xhr",function($http, SystemMessages, $rootScope) {


    this.get = function (url, sucess,error) {
        $http.get(url).then(function (data) {
                if (typeof sucess !== "undefined" && data.status===200) {
                    sucess(data);
                }else{
                    if (typeof error !== "undefined") {
                        error(data);
                    }
                }

            },
            function(data) {
                if (typeof error !== "undefined") {
                    error(data);
                }


            });
    };

    this.post = function (url, data, sucess,error) {

        data[$rootScope.csrf.parameterName] = $rootScope.csrf.token;
        $http.defaults.headers.post['X-CSRF-TOKEN'] = data._csrf;
        $http({
            method: 'POST',
            url: url,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'X-CSRF-TOKEN': data._csrf

            },
            transformRequest: function (obj) {
                let str = [];
                for (let p in obj) {
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                }
                return str.join("&");
            },
            data: data
        }).then(
            function(data){
                if(typeof sucess !== "undefined"){
                    sucess(data);
                }
            },
            function(data) {
                if (typeof error !== "undefined") {
                    error(data);
                }
            }
        );
    };
});