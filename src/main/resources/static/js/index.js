/*$('.table > tbody > tr').click(function() {
    console.log(this);
});

$(".table > tbody > tr > input:checkbox").click(function() {
    this.prop('checked', !this.prop('checked'));
});*/

$(":checkbox").click(function() {
    var $input = $(this);
    $input.prop('checked', !$input.prop('checked'));
});

$('.table > tbody > tr').click(function() {
    var $input = $("th > input", this);
    $input.prop('checked', !$input.prop('checked'));
});
