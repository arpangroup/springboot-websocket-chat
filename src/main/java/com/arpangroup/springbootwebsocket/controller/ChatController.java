package com.arpangroup.springbootwebsocket.controller;

import com.arpangroup.springbootwebsocket.model.request.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class ChatController {
    Logger LOG = LoggerFactory.getLogger(getClass().getSimpleName());

    public List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


//    @MessageMapping("/chat/{to}")
//    public void sendMessage(@DestinationVariable String to, ChatMessage chatMessage){
//        System.out.println("handling send message: " + chatMessage + " to: " + to);
//        boolean isUserExist = UserStorage.getInstance().getUsers().contains(to);
//        if(isUserExist){
//            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, chatMessage);
//        }
//    }
    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        System.out.println("handling send message: " + chatMessage + " to: " + to);
        chatMessage.setTimestamp(new Date().getTime());
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, chatMessage);
    }


    /* /app/questions" */
    @MessageMapping("/questions")
    @SendTo("/topic/public")
    public ChatMessage processQuestion(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setContent(chatMessage.getContent().toUpperCase() + " by "+ chatMessage.getSender()+ new Date().getTime());
        /*
            Even if we processed that message, We dont know what to with this STOMP message
            So The return of our controllers will be send to the broker,
            so its the broker role what needs to done with that particular payload.
            Hence Broker will takecare of broadcasting the payload
         */
        return chatMessage;
    }

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
