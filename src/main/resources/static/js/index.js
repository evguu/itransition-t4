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
