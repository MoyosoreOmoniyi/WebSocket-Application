package com.oapps.applications.websocket.controllers;

import com.oapps.applications.websocket.pojos.Message;
import com.oapps.applications.websocket.pojos.ResponseMessage;
import com.oapps.applications.websocket.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageController {

    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/message") //client sends to ws/message
    @SendTo("/topic/messages") // response sent to client subscriber who receives on
    public ResponseMessage getMessage (final Message message) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendGlobalNotification();
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }

    @MessageMapping("/private-message") //send to ws/private-message
    @SendToUser("/topic/private-messages") // response sent to subscriber who receives on
    public ResponseMessage getPrivateMessage (final Message message, final Principal principal) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendPrivateNotification(principal.getName());
        return new ResponseMessage(HtmlUtils.htmlEscape("Sending private message to user: " + principal.getName() + ": " +  message.getMessageContent()));
    }
}
