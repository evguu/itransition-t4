function getListOfCheckedRowIds(){
    $(".table > tbody > tr").each((k,v) => {

        if($(v).prop('checked')) {
        }

    });

}

$("#unblock").click(()=>{
    $.ajax({
        type: "POST",
        url: 'command',
        data: {indexes: [1, 2, 3, 4], action: "unblock"},
        success: function (result) {
            console.log(result);
        }
    });
});

$("#block").click(()=>{
    $.ajax({
        type: "POST",
        url: 'command',
        data: {indexes: [1, 2, 3, 4], action: "block"},
        success: function (result) {
            console.log(result);
        }
    });
});

$("#delete").click(()=>{
    $.ajax({
        type: "POST",
        url: 'command',
        data: {indexes: [1, 2, 3, 4], action: "delete"},
        success: function (result) {
            console.log(result);
        }
    });
});
