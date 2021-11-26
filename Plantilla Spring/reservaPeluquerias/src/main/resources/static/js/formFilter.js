$(document).ready(function(){
    $("#add").css("cursor", "pointer");
    $("#form").toggle();
    $("#add").click(function(){
        $("#form").toggle();
    });
});