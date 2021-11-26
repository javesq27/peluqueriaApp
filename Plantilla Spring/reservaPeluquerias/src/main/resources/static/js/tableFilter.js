$(document).ready(function(){
    $("#filterButton").click(function() {
        var datos = $("#filter").val();
        var list = $("table").find(".list");
        list.hide();
        list.filter(function(){
            return $(this).find('.branch').text().indexOf(datos) > -1
        }).show()
    });
});