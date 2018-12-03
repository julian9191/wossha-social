package com.wossha.social.configure;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {

	@Value("${activemq.broker-url}")
    private String brokerUrl;
	
	@Value("${activemq.username}")
    private String username;
	
	@Value("${activemq.password}")
    private String password;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("pictures.queue");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(this.brokerUrl);
        factory.setPassword(this.username);
        factory.setUserName(this.password);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }

}
