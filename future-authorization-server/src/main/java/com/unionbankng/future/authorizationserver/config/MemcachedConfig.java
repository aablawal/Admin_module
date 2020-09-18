package com.unionbankng.future.authorizationserver.config;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MemcachedConfig {

    @Value( "${memcached.service.host}" )
    private String memcachedHost;

    @Value( "${memcached.service.port}" )
    private int memcachedPort;

    Logger logger = LoggerFactory.getLogger(MemcachedConfig.class);

    @Bean
    public MemcachedClient memcachedClient() {

        MemcachedClient client = null;

        try {

            client = new XMemcachedClient(memcachedHost,memcachedPort);

        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.info("Memcached encountered an error : {}",e.getMessage());
        }

        return client;

    }
}
