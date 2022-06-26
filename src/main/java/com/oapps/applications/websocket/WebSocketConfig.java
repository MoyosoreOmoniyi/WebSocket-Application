package com.oapps.applications.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // The SockJS client will attempt to connect to the /ws endpoint and use the best available transport to establish a connection(websocket).
                .setHandshakeHandler(new UserHandshakeHandler())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // This enables a simple memory-based message broker to carry the response message back to the client on destinations prefixed with /topic
        registry.setApplicationDestinationPrefixes("/app"); // This designates the /app prefix for messages that are bound for methods annotated with @MessageMapping. The prefix will be used to define all the message mappings. For example, /app/message is the endpoint that the MessageController.getMessage() method is mapped to handle.
    }
}
