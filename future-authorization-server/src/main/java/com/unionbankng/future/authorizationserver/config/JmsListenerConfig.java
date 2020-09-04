package com.unionbankng.future.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsListenerConfig {

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory factory, JsonMessageConverter converter) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(factory);
        template.setMessageConverter(converter);
        template.setPubSubDomain(false); // false for a Queue, true for a Topic
        return template;
    }
}