$(document).ready(() => {
    $('#mainCard a').editable();
});

function updateSummary(object) {
    $(object).closest('form').submit();
}

function deleteSummary(object) {
    sendAjax('/deleteSummary?summaryId=' + getValue(object), (data) => $('#' + data).remove(), badRequest)
}

function getValue(object) {
    return $(object).closest('tr').attr('id');
}

function badRequest(data) {
    console.log(data);
    alert('error');
}

function sendAjax(url, success, error) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        url: url,
        success: success,
        error: error
    });
}

// function showFiltredSummaries(summaries) {
//     tbody = $('#summariesTable tbody');
//
//     tbody.empty();
//     summaries.each((i, item) => {
//        tbody.append(
//          $('<tr></tr>').attr('id', summaries.id)
//        );
//     });
// }