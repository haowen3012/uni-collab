package it.unicollab.bh.controller.notification;

import it.unicollab.bh.model.message.Message;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;



@Controller
public class WebSocketController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Message send(final Message message) throws Exception {

        return message;

    }
    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getDestination().getUserName(), "/specific", message);
    }

}

