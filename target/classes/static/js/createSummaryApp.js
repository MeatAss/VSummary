$(document).ready(function () {
    createMarkdown(document.getElementById("textSummary"));

    $('#summaryTags').on('input', function(){ sendTag(this); });
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
                    textVal = $('#summaryTags').val();
                    textVal = textVal.slice(0, textVal.length - getLastWord($('#summaryTags')).length) + $(this).text();
                    $('#summaryTags').val(textVal);
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
    form = $('#addSummaryForm');
    if (!form[0].checkValidity() | !checkValidityMarkdown(form))
        return;

    spis = {
        "id" : form.find('#idSummary').val(),
        "nameSummary" : form.find('#nameSummary').val(),
        "shortDescription" : form.find('#shortDescription').val(),
        "specialtyNumber" : form.find('#specialtyNumber').val(),
        "textSummary" : form.find('#textSummary').val()
    };

    url = '/createSummary/addSummary?userId=' + form.find('#userId').val() + '&summaryTags=' + form.find('#summaryTags').val().split(' ');

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: JSON.stringify(spis),
        success: (data) => {
            window.location.href = data;
        },
        error: (data) => {
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