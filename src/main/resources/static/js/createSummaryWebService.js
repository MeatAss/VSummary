var userName = "";

$( document ).ready(function () {
    connect();
});

function connect() {
    socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/searchData', function(results) {
            updateSearchResults(JSON.parse(results.body));
        });
        stompClient.subscribe('/user/queue/updateTags', function(results) {
            updateTags(JSON.parse(results.body));
        });
        stompClient.subscribe('/user/queue/fileLoaded', function(results) {
            dropLoadInimate(JSON.parse(results.body).message);
        });

        userName = frame.headers['user-name'];
    });
}

function sendToServer(url, data) {
    stompClient.send(url, {}, JSON.stringify(data));
}