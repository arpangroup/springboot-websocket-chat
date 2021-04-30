package com.arpangroup.springbootwebsocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    Logger LOG = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/pureeats");
        registry.addEndpoint("/pureeats").withSockJS();
//        registry.addEndpoint("/pureeats")
//                .setAllowedOrigins("*")
//                .setHandshakeHandler(new CustomHandshakeHandler())
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * SimpleBroker is the embedded in-memory broker provided by Spring
         */
        registry.enableSimpleBroker("/topic", "/queue");

        /**
         * Application destinations are forwarded to controllers
         * Here everything start with /app will forword to a controller
         * e.g. /app/questions will forward to controller
         */
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/secured/user");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(WebSocketHandler webSocketHandler) {
                return new EmaWebSocketHandlerDecorator(webSocketHandler);
            }
        });
    }

    class QuestionHandler extends TextWebSocketHandler{
        private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            sessions.add(session);
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            for (WebSocketSession s : sessions){
                try {
                    s.sendMessage(message);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

//    private class CustomHandshakeHandler extends DefaultHandshakeHandler{
//        // Custom class for storing principal
//        @Override
//        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//            // Generate principal with UUID as name
//            try{
//                String uuid = UUID.randomUUID().toString();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return new UsernamePasswordAuthenticationToken(uuid, null);
//        }
//    }




    public static class EmaWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

        private final Logger logger = LoggerFactory.getLogger(EmaWebSocketHandlerDecorator.class);

        public EmaWebSocketHandlerDecorator(WebSocketHandler webSocketHandler) {
            super(webSocketHandler);
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            super.handleMessage(session, updateBodyIfNeeded(message));
        }

        /**
         * Updates the content of the specified message. The message is updated only if it is
         * a {@link TextMessage text message} and if does not contain the <tt>null</tt> character at the end. If
         * carriage returns are missing (when the command does not need a body) there are also added.
         */
        private WebSocketMessage<?> updateBodyIfNeeded(WebSocketMessage<?> message) {
            if (!(message instanceof TextMessage) || ((TextMessage) message).getPayload().endsWith("\u0000")) {
                return message;
            }

            String payload = ((TextMessage) message).getPayload();

            final Optional<StompCommand> stompCommand = getStompCommand(payload);

            if (!stompCommand.isPresent()) {
                return message;
            }

            if (!stompCommand.get().isBodyAllowed() && !payload.endsWith("\n\n")) {
                if (payload.endsWith("\n")) {
                    payload += "\n";
                } else {
                    payload += "\n\n";
                }
            }

            payload += "\u0000";

            return new TextMessage(payload);
        }

        /**
         * Returns the {@link StompCommand STOMP command} associated to the specified payload.
         */
        private Optional<StompCommand> getStompCommand(String payload) {
            final int firstCarriageReturn = payload.indexOf('\n');

            if (firstCarriageReturn < 0) {
                return Optional.empty();
            }

            try {
                return Optional.of(
                        StompCommand.valueOf(payload.substring(0, firstCarriageReturn))
                );
            } catch (IllegalArgumentException e) {
                logger.trace("Error while parsing STOMP command.", e);

                return Optional.empty();
            }
        }
    }
}

