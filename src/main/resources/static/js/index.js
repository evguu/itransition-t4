function getListOfCheckedRowIds(){
    return $("table").bootstrapTable('getSelections').map(e => e[1]);
}

function sendCommandWithCheckedIds(cmd){
    $.ajax({
        type: "POST",
        url: 'command',
        data: {indexes: getListOfCheckedRowIds(), action: cmd},
        success: function (result) {
            console.log(result);
        }
    });
}

$("#unblock").click(()=>{
    sendCommandWithCheckedIds("unblock");
});

$("#block").click(()=>{
    sendCommandWithCheckedIds("block");
});

$("#delete").click(()=>{
    sendCommandWithCheckedIds("delete");
});
