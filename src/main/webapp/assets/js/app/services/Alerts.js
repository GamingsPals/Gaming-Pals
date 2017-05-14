app.service("Alerts",function(SweetAlert,localization){


    this.confirm = function (object) {
        SweetAlert.swal({
            title: object.title,
            text : object.text,
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: localization.yes,
            cancelButtonText: localization.cancel,
            closeOnConfirm: false
        },function(isConfirm){
            if(isConfirm){
                if(typeof object.callback !=="undefined"){
                    object.callback();
                }
                swal(object.confirmtitle, object.confirmtext, "success");
            }
        });
    }

});