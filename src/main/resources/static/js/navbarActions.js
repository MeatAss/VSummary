$(document).ready(() => {
    $('#navbarNav').find('a[href="'+ window.location.pathname + '"]').parent().addClass('active');
});

function showAuthentication(type) {
    sendAjax('/openAuthentication', {"message": type}, changeURL);
}

function changeURL(url) {
    window.location.href = url;
}

function sendAjax(url, data, success) {
    console.log(JSON.stringify(data));
    $.ajax({
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        url: url,
        data: JSON.stringify(data),
        success: function (data) {
            success(data.url);
        }
    });
}