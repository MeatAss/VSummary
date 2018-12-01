$( document ).ready(function () {
    connect();
});

function connect() {
    socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/personalArea/filter', function(results) {
            showFiltredSummaries(JSON.parse(results.body));
        });
    });
}

function sendToServer(url, data) {
    stompClient.send(url, {}, JSON.stringify(data));
}