package com.unionbankng.future.authorizationserver.config;

import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AbstractSSMConfiguration;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.providers.xmemcached.XMemcachedConfiguration;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@RefreshScope
@Configuration
public class MemcachedConfig extends AbstractSSMConfiguration {

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

    @Bean
    @Override
    public CacheFactory defaultMemcachedClient() {
        String serverString = memcachedHost+":"+memcachedPort;
        final XMemcachedConfiguration conf = new XMemcachedConfiguration();
        conf.setUseBinaryProtocol(true);

        final CacheFactory cf = new CacheFactory();
        cf.setCacheClientFactory(new MemcacheClientFactoryImpl());
        cf.setAddressProvider(new DefaultAddressProvider(serverString));
        cf.setConfiguration(conf);
        return cf;
    }
}
