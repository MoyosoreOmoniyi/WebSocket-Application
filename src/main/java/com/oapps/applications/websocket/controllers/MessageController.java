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

    /**<p>Get Message</p>
     * @MessageMapping("/message") annotation ensures that when a client sends a message to /message the getMessage() method is called.
     * @SendTo("/topic/messages")  annotation ensures that the response of the getMessage() method is broadcast to all subscribers(e.g: the client) of /topic/messages
     * @param message The message sent from client
     * @return response sent back to the client
     * @throws InterruptedException
     */
    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ResponseMessage getMessage (final Message message) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendGlobalNotification();
        return new ResponseMessage("I will respond to this message: " + HtmlUtils.htmlEscape(message.getMessageContent()));
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage (final Message message, final Principal principal) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendPrivateNotification(principal.getName());
        return new ResponseMessage(HtmlUtils.htmlEscape("Sending private message to user: " + principal.getName() + ": " +  message.getMessageContent()));
    }
}
