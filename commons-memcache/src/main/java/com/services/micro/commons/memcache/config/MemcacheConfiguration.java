package com.services.micro.commons.memcache.config;

import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.spymemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.SSMCache;
import com.google.code.ssm.spring.SSMCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableCaching
@ImportResource({"classpath*:simplesm-context.xml"})
@ConditionalOnProperty(name = "service.memcache.enabled")
@EnableConfigurationProperties(MemcacheConfigurationProperties.class)
public class MemcacheConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcacheConfiguration.class);

    @Autowired
    private MemcacheConfigurationProperties memcacheConfigurationProperties;

    @Autowired
    private MemcacheClientFactoryImpl memcacheClientFactoryImpl;

    @Autowired
    private DefaultAddressProvider defaultAddressProvider;


    @Autowired
    private CacheConfiguration cacheConfiguration;

    @Autowired
    private CacheFactory cacheFactory;

    @Autowired
    private SSMCache ssmCache;



    @Bean
    public DefaultAddressProvider defaultAddressProvider() {
        LOGGER.info("creating DefaultAddressProvider for memcache with endpoint " + memcacheConfigurationProperties.getServers());
        DefaultAddressProvider defaultAddressProvider = new DefaultAddressProvider();
        defaultAddressProvider.setAddress(memcacheConfigurationProperties.getServers());
        return defaultAddressProvider;
    }


    @Bean
    public CacheConfiguration cacheConfiguration() {
        LOGGER.info("creating CacheConfiguration for memcache");
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setConsistentHashing(true);
        cacheConfiguration.setUseBinaryProtocol(memcacheConfigurationProperties.isBinary());
        return cacheConfiguration;
    }

    @Bean
    public MemcacheClientFactoryImpl memcacheClientFactoryImpl() {
        return new MemcacheClientFactoryImpl();
    }


    @DependsOn({"cacheBase", "memcacheClientFactoryImpl", "defaultAddressProvider", "cacheConfiguration"})
    @Bean
    public CacheFactory cacheFactory() {
        LOGGER.info("creating CacheFactory for memcache");
        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName(memcacheConfigurationProperties.getName());
        cacheFactory.setCacheClientFactory(memcacheClientFactoryImpl);
        cacheFactory.setAddressProvider(defaultAddressProvider);
        cacheFactory.setConfiguration(cacheConfiguration);
        return cacheFactory;
    }

    @Bean
    @DependsOn("cacheFactory")
    public SSMCache ssmCache() {
        LOGGER.info("creating SSMCache for memcache with ttl " + memcacheConfigurationProperties.getTtl());
        return  new SSMCache(cacheFactory.getCache(), memcacheConfigurationProperties.getTtl(), false);
    }

    @Bean
    @DependsOn("ssmCache")
    public SSMCacheManager ssmCacheManager() {
        SSMCacheManager ssmCacheManager = new SSMCacheManager();
        Set<SSMCache> ssmCacheSet = new HashSet<>();
        ssmCacheSet.add(ssmCache);
        ssmCacheManager.setCaches(ssmCacheSet);
        return ssmCacheManager;
    }

}

