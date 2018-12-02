$( document ).ready(function () {
    connect();
});

function connect() {
    socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    // stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/main/newComment', function(results) {
            addComment(JSON.parse(results.body));
        });
        stompClient.subscribe('/topic/main/updateRating', function(results) {
            changeRating(JSON.parse(results.body));
        });
    });
}

function sendToServer(url, data) {
    stompClient.send(url, {}, JSON.stringify(data));
}