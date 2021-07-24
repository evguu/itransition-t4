$(".table > thead :checkbox").click(function() {
    let input = $(this);
    let newCondition = input.prop('checked');
    $(".table > tbody :checkbox").prop('checked', newCondition);
});

$(".table > tbody :checkbox").click(function() {
    let input = $(this);
    input.prop('checked', !input.prop('checked'));
});

$('.table > tbody > tr').click(function() {
    let input = $("th > input", this);
    input.prop('checked', !input.prop('checked'));
    setHeadCheckboxOnIfAllSet();
});

function setHeadCheckboxOnIfAllSet(){
    let headCheckbox = $(".table > thead :checkbox");
    let areAllSet = true;
    $(".table > tbody :checkbox").each((k,v) => {
        if(!$(v).prop('checked')) {
            areAllSet = false;
        }
    });
    headCheckbox.prop('checked', areAllSet);
}

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
