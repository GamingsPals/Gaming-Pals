app.controller("LegalController", function($scope,dialog){
    let d = dialog.open("legal");
    dialog.redirect(d,()=>{

    });
});