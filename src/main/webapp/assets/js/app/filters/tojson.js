app.filter ('tojson', function(){


        return function (obj) {
            return JSON.parse(obj);
        }
    });