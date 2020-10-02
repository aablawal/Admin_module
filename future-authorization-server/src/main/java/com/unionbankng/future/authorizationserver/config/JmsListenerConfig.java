package com.unionbankng.future.authorizationserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsListenerConfig {

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(ConnectionFactory connectionFactory) {
        CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory);
        factory.setReconnectOnException(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory factory, JsonMessageConverter converter) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(factory);
        template.setMessageConverter(converter);
        template.setPubSubDomain(false); // false for a Queue, true for a Topic
        return template;
    }
}