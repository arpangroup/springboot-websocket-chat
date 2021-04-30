'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var userNameElement = document.querySelector('#userName');
var chatWithElement = document.querySelector('#chatWith');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        userNameElement.innerHTML = username;
        chatWithElement.value = username;

        var socket = new SockJS('/pureeats');
        stompClient = Stomp.over(socket);

        var headers = {
            'Auth-Token': '12345token',
            'userId': "123456",
            'userName': username
        };

        stompClient.connect(headers, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected(frame) {
    console.log("CONNECTED_TO: " + frame);
    // Subscribe to the Public Topic
   stompClient.subscribe('/topic/public', onMessageReceived);
    //stompClient.subscribe('/topic/queue/private', onMessageReceived);
    stompClient.subscribe('/topic/messages/' + username, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.register", {}, JSON.stringify({sender: username, type: 'JOIN'}));

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        var header = {};

        //stompClient.send("/app/chat.send", header, JSON.stringify(chatMessage));
        //stompClient.send("/app/questions", header, JSON.stringify(chatMessage));
        console.log("INSIDE_SEND: "+chatWithElement.value);
        stompClient.send("/app/chat/" + chatWithElement.value, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    console.log("onMessageReceived: ", payload);
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', send, true)
