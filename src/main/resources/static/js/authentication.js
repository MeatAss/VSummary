var subForm = null;

$(document).ready(() => {
    subForm = $('#subFormtrue');
    subForm.on('submit', (e) => {
        e.preventDefault();
        return false;
    });
});

function redirectRegistration() {
    window.location.href = '/registration';
}

function redirectLogin() {
    window.location.href = '/login';
}

function submitData() {
    sendAjax(subForm.attr('action'), subForm.serialize(), () => {}, errorResponse);
}

function errorResponse(response) {
    if (response.status === 400)
        $('#errorText').text(response.responseText);
}

function sendAjax(url, data, success, error) {
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: success,
        error: error
    });
}