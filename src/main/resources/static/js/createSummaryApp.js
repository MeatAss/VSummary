$(document).ready(function () {
    $('#sumaryTags').on('input', function(){ sendTag(this); });
    $(document).on('click', function(){ $('#tagsUL').addClass('invisible'); });

    $("#addSummaryForm").on('submit', function (e) {
        e.preventDefault();
    });
});

function updateTags(results) {
    $('#tagsUL').empty();

    $(results).each(function(i, result) {
        $('#tagsUL').append(
            $('<li></li>')
                .addClass('nav-item')
                .on('click', function(){
                    textVal = $('#sumaryTags').val();
                    textVal = textVal.slice(0, textVal.length - getLastWord($('#sumaryTags')).length) + $(this).text();
                    $('#sumaryTags').val(textVal);
                    $('#tagsUL').addClass('invisible');
                })
                .text(result.message)
        );
    });

    $('#tagsUL').removeClass('invisible');
}

function updateSearchResults(results) {
    $(results).each(function(i, result) {
        console.log(result.message);
    });
}

function sendTag(elem) {
    sendToServer("/app/tags/update", {'message' : getLastWord(elem)});
}

function getLastWord(elem) {
    return  $(elem).val().slice(($(elem).val().lastIndexOf(" ")) + 1);
}

function sendData() {
    if (!$('#addSummaryForm')[0].checkValidity())
        return;

    spis = {
        "nameSummary" : $('#nameSummary').val(),
        "shortDescription" : $('#shortDescription').val(),
        "specialtyNumber" : $('#specialtyNumber').val(),
        "sumaryTags" : $('#sumaryTags').val(),
        "textSummary" : $('#textSummary').val()
    };

    $.ajax({
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        url: '/createSummary/addSummary',
        data: JSON.stringify(spis),
        success: function(data) {
            alert(data);
        }
    });
}

function sendSearchData() {
    sendToServer("/app/search/update", {'message' : $('#searchText').val()});
}

function markdownClick(elem) {
    isCheck = $(elem).hasClass('text-info');
    textSummary = $('#textSummary');

    if (isCheck) {
        $(elem).removeClass('text-info');
        $(elem).addClass('text-secondary');
        textSummary.addClass('invisible position-absolute');
        $('#divTextId').append($($('<div></div>')
            .attr('id', 'markdownDiv'))
            .html(markdown.toHTML(textSummary.val()))
        );
    } else {
        $(elem).removeClass('text-secondary');
        $(elem).addClass('text-info');
        $('#markdownDiv').remove();
        textSummary.removeClass('invisible position-absolute');
    }
}