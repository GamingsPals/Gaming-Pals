
app.service("SystemMessages", function($timeout){

    this.color="";
    this.message = "";
    this.show = false;


    this.okmessage = function(message){
        this.color ="bg-green3";
        this.message = `<i class="fa fa-check"></i> ${message}`;
        this.show = true;
        let object = this;
        $timeout(function(){
            object.show = false;
        },2000);
    };

    this.errormessage = function(message){
        this.color ="bg-red3";
        this.message = `<i class="fa fa-close"></i> ${message}`;
        this.show = true;
        let object = this;
        $(".message-system").show();
        $timeout(function(){
            object.show = false;
            $(".message-system").hide();
        },3000);
    }

});