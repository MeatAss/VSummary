function showSimpleAuthentication() {
    sendAjax('/openAuthentication', {"message":"SIMPLE"}, changeURL);
}

function showFacebookAuthentication() {
    sendAjax('/openAuthentication', {"message":"FACEBOOK"}, changeURL);
}

function showTwitterAuthentication() {
    sendAjax('/openAuthentication', {"message":"TWITTER"}, changeURL);
}

function showVKontakteAuthentication() {
    sendAjax('/openAuthentication', {"message":"VKONTAKTE"}, changeURL);
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