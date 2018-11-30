$(document).ready(() => {
   $('.select_role').editable({
       source: [
           {value: "USER", text: 'User'},
           {value: "ADMINISTRATOR", text: 'Administrator'}
       ]
   });
    $('.select_status').editable({
        source: [
            {value: "true", text: 'unlock'},
            {value: "false", text: 'lock'}
        ]
    });
});

function showUser(event) {
    console.log("/administratorArea/showUser?userId=" + $(event).closest('tr').attr('id'));
    sendAjax("/administratorArea/showUser?userId=" + $(event).closest('tr').attr('id'),
        (data) => {window.location.href = data},
        (data) => {alert("error")}
        )
}

function sendAjax(url, success, error) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        success: success,
        error: error
    });
}