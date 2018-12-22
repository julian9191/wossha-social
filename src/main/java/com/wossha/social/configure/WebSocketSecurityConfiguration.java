package com.wossha.social.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        System.out.println("");
    	messages.anyMessage().hasRole("USER");
    }

    @Override
    protected boolean sameOriginDisabled() {
        // We need to access this directly from apps, so can't do cross-site checks
        return true;
    }

}
