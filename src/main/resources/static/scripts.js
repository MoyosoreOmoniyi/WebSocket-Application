// Try to set up WebSocket connection with the handshake at "http://localhost:8900/ws"
let sock = new SockJS("http://localhost:8900/ws");

// Create a new Stomp Client object with the WebSocket endpoint
let stompClient = Stomp.over(sock);

// Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
stompClient.connect({}, frame => {
    console.log('Connected: ' + frame);
    // Subscribe to "/topic/messages". Whenever a message arrives add the text in a list-item element in the unordered list.
    stompClient.subscribe("/topic/messages", payload => {

        let message_list = document.getElementById('message-list');
        let message = document.createElement('li');

        message.appendChild(document.createTextNode(JSON.parse(payload.body).content));
        message_list.appendChild(message);

    });
    
    stompClient.subscribe("/user/topic/private-messages", payload => {
    
            let message_list = document.getElementById('message-list');
            let message = document.createElement('li');
    
            message.appendChild(document.createTextNode(JSON.parse(payload.body).content));
            message_list.appendChild(message);
    
    });
});

// Take the value in the ‘message-input’ text field and send it to the server with empty headers.
function sendMessage(){
    console.log("sending message");
    let input = document.getElementById("message-input");
    let message = input.value;

    stompClient.send('/app/message', {}, JSON.stringify({messageContent: message}));
}

function sendPrivateMessage(){
    console.log("sending private message");
    let input = document.getElementById("private-message-input");
    let message = input.value;

    stompClient.send('/app/private-message', {}, JSON.stringify({messageContent: message}));
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}