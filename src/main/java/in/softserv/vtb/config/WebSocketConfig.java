package in.softserv.vtb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
		//config.setUserDestinationPrefix("/user");
		
		//config.setApplicationDestinationPrefixes("/ws");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/updateMapLocation");
		//registry.addEndpoint("/updateMapLocation").withSockJS();
		//registry.addEndpoint("/updateMapLocation").setAllowedOrigins("*").withSockJS();
		registry.addEndpoint("/updateMapLocation").setAllowedOriginPatterns("*").withSockJS();
		
		//registry.addEndpoint("/updateMapLocation").setHandshakeHandler(new UserHandshakeHandler()).withSockJS();
		
	}

}
