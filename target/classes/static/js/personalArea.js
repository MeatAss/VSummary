$(document).ready(() => {
    $('#mainCard a').editable();

    $('#filter_input').on('input', filterChange);
    $('#dropdownSortingArea a').on('click', (event) => {
        event.preventDefault();
        item = $(event.currentTarget);
        button = $('#dropdownSorting');

        $('#dropdownSortingArea a').removeClass('active');
        item.addClass('active');

        button.text(item.text());
        button.attr('value', item.attr('value'));

        $('#filter_input').trigger('input');
    });

    $('.update_icon').on('click', updateSummary);
    $('.delete_icon').on('click', deleteSummary);
});

function updateSummary(event) {
    $(event.currentTarget).closest('form').submit();
}

function deleteSummary(event) {
    sendAjax('/deleteSummary?summaryId=' + getValue(event.currentTarget), (data) => $('#' + data).remove(), badRequest)
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

function filterChange(event) {
    sendToServer("/app/personalArea/filter", {'filter' : $(event.currentTarget).val(), 'sorting' : $('#dropdownSorting').attr('value')});
}

function showFiltredSummaries(response) {
    tBody = $('#summariesTable tbody');
    tBody.empty();

    $($(response)[0].summaries).each((i, summary) => {
        tBody.append(createTr(summary, tBody.attr('value')));
    });
}

function createTr(summary, originalUserId) {
    tr = $('<tr></tr>').attr('id', summary.id);
    tr.append($('<th></th>').attr('scope', 'row').text(summary.nameSummary));
    tr.append($('<td></td>').text(summary.shortDescription));

    form = $('<form></form>');
    form.attr('method', 'post');
    form.attr('action', '/updateSummary');
    form.append(
      $('<input>').attr('name', 'userId').attr('type', 'hidden').val(originalUserId)
    );
    form.append(
        $('<input>').attr('name', 'summaryId').attr('type', 'hidden').val(summary.id)
    );
    form.append(
        $('<i></i>').addClass('fas fa-pencil-alt').on('click', updateSummary)
    );
    tr.append($('<td></td>').append(form));

    tr.append(
        $('<i></i>').addClass('far fa-trash-alt').on('click', deleteSummary)
    );

    return tr;
}