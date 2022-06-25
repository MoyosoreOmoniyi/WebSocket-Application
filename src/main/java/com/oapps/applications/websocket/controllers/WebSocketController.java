package com.oapps.applications.websocket.controllers;

import com.oapps.applications.websocket.pojos.Message;
import com.oapps.applications.websocket.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import javax.ws.rs.core.Response;

@RestController
public class WebSocketController {

    @Autowired
    private WebSocketService service;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody Message message) {
        service.notifyFrontend(message.getMessageContent().concat(": I was sent to all users"));
    }

    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id, @RequestBody final Message message) {
        service.notifyUser(id, message.getMessageContent() + ": I was sent to user with id: " + id);
    }
}
