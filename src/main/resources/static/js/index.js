function getListOfCheckedRowIds(){
    return $("table").bootstrapTable('getSelections').map(e => e[1]);
}

function redirectToLogout(){
    window.location.replace("logout");
}

function sendCommandWithCheckedIds(cmd){
    $.ajax({
        type: "POST",
        url: 'command',
        data: {indexes: getListOfCheckedRowIds(), action: cmd},
        success: function (result) {
            if(JSON.parse(result).isLoggedOut){
                redirectToLogout();
            }
            else {
                window.location.reload(true);
            }
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
