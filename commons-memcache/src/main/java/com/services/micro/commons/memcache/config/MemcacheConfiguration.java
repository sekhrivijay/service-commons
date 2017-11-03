package com.services.micro.commons.memcache.config;

import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.aop.CacheBase;
import com.google.code.ssm.aop.InvalidateAssignCacheAdvice;
import com.google.code.ssm.aop.InvalidateMultiCacheAdvice;
import com.google.code.ssm.aop.InvalidateSingleCacheAdvice;
import com.google.code.ssm.aop.ReadThroughAssignCacheAdvice;
import com.google.code.ssm.aop.ReadThroughMultiCacheAdvice;
import com.google.code.ssm.aop.ReadThroughSingleCacheAdvice;
import com.google.code.ssm.aop.UpdateAssignCacheAdvice;
import com.google.code.ssm.aop.UpdateMultiCacheAdvice;
import com.google.code.ssm.aop.UpdateSingleCacheAdvice;
import com.google.code.ssm.aop.counter.DecrementCounterInCacheAdvice;
import com.google.code.ssm.aop.counter.IncrementCounterInCacheAdvice;
import com.google.code.ssm.aop.counter.ReadCounterFromCacheAdvice;
import com.google.code.ssm.aop.counter.UpdateCounterInCacheAdvice;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.spymemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.SSMCache;
import com.google.code.ssm.spring.SSMCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableCaching
//@ImportResource({"classpath*:simplesm-context.xml"})
//@ImportResource({"classpath*:memcache-context.xml"})
@ConditionalOnProperty(name = "service.memcache.enabled")
@EnableConfigurationProperties(MemcacheConfigurationProperties.class)
public class MemcacheConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcacheConfiguration.class);

    @Autowired
    private MemcacheConfigurationProperties memcacheConfigurationProperties;

//    @Autowired
//    private MemcacheClientFactoryImpl memcacheClientFactoryImpl;

//    @Autowired
//    private DefaultAddressProvider defaultAddressProvider;
//
//    @Autowired
//    private CacheConfiguration cacheConfiguration;

    @Autowired
    private SSMCache ssmCache;

    @Autowired
    private CacheBase cacheBase;

    @Bean
    public CacheBase cacheBase() {
        return new CacheBase();
    }


    @Bean
    public ReadThroughSingleCacheAdvice readThroughSingleCacheAdvice() {
        ReadThroughSingleCacheAdvice readThroughSingleCacheAdvice = new ReadThroughSingleCacheAdvice();
        readThroughSingleCacheAdvice.setCacheBase(cacheBase);
        return readThroughSingleCacheAdvice;
    }

    @Bean
    public ReadThroughMultiCacheAdvice readThroughMultiCacheAdvice() {
        ReadThroughMultiCacheAdvice readThroughMultiCacheAdvice = new ReadThroughMultiCacheAdvice();
        readThroughMultiCacheAdvice.setCacheBase(cacheBase);
        return readThroughMultiCacheAdvice;
    }

    @Bean
    public ReadThroughAssignCacheAdvice readThroughAssignCacheAdvice() {
        ReadThroughAssignCacheAdvice readThroughAssignCacheAdvice = new ReadThroughAssignCacheAdvice();
        readThroughAssignCacheAdvice.setCacheBase(cacheBase);
        return readThroughAssignCacheAdvice;
    }

    @Bean
    public UpdateSingleCacheAdvice updateSingleCacheAdvice() {
        UpdateSingleCacheAdvice updateSingleCacheAdvice = new UpdateSingleCacheAdvice();
        updateSingleCacheAdvice.setCacheBase(cacheBase);
        return updateSingleCacheAdvice;
    }

    @Bean
    public UpdateMultiCacheAdvice updateMultiCacheAdvice() {
        UpdateMultiCacheAdvice updateMultiCacheAdvice = new UpdateMultiCacheAdvice();
        updateMultiCacheAdvice.setCacheBase(cacheBase);
        return updateMultiCacheAdvice;
    }

    @Bean
    public UpdateAssignCacheAdvice updateAssignCacheAdvice() {
        UpdateAssignCacheAdvice updateAssignCacheAdvice = new UpdateAssignCacheAdvice();
        updateAssignCacheAdvice.setCacheBase(cacheBase);
        return updateAssignCacheAdvice;
    }

    @Bean
    public InvalidateSingleCacheAdvice invalidateSingleCacheAdvice() {
        InvalidateSingleCacheAdvice invalidateSingleCacheAdvice = new InvalidateSingleCacheAdvice();
        invalidateSingleCacheAdvice.setCacheBase(cacheBase);
        return invalidateSingleCacheAdvice;
    }

    @Bean
    public InvalidateMultiCacheAdvice invalidateMultiCacheAdvice() {
        InvalidateMultiCacheAdvice invalidateMultiCacheAdvice = new InvalidateMultiCacheAdvice();
        invalidateMultiCacheAdvice.setCacheBase(cacheBase);
        return invalidateMultiCacheAdvice;
    }

    @Bean
    public InvalidateAssignCacheAdvice invalidateAssignCacheAdvice() {
        InvalidateAssignCacheAdvice invalidateAssignCacheAdvice = new InvalidateAssignCacheAdvice();
        invalidateAssignCacheAdvice.setCacheBase(cacheBase);
        return invalidateAssignCacheAdvice;
    }

    @Bean
    public IncrementCounterInCacheAdvice incrementCounterInCacheAdvice() {
        IncrementCounterInCacheAdvice incrementCounterInCacheAdvice = new IncrementCounterInCacheAdvice();
        incrementCounterInCacheAdvice.setCacheBase(cacheBase);
        return incrementCounterInCacheAdvice;
    }

    @Bean
    public DecrementCounterInCacheAdvice decrementCounterInCacheAdvice() {
        DecrementCounterInCacheAdvice decrementCounterInCacheAdvice = new DecrementCounterInCacheAdvice();
        decrementCounterInCacheAdvice.setCacheBase(cacheBase);
        return decrementCounterInCacheAdvice;
    }

    @Bean
    public ReadCounterFromCacheAdvice readCounterFromCacheAdvice() {
        ReadCounterFromCacheAdvice readCounterFromCacheAdvice = new ReadCounterFromCacheAdvice();
        readCounterFromCacheAdvice.setCacheBase(cacheBase);
        return readCounterFromCacheAdvice;
    }


    @Bean
    public UpdateCounterInCacheAdvice updateCounterInCacheAdvice() {
        UpdateCounterInCacheAdvice updateCounterInCacheAdvice = new UpdateCounterInCacheAdvice();
        updateCounterInCacheAdvice.setCacheBase(cacheBase);
        return updateCounterInCacheAdvice;
    }




//    @Bean
//    @ConditionalOnMissingBean
//    public DefaultAddressProvider defaultAddressProvider() {
//        LOGGER.info("creating DefaultAddressProvider for memcache with endpoint " + memcacheConfigurationProperties.getServers());
//        DefaultAddressProvider defaultAddressProvider = new DefaultAddressProvider();
//        defaultAddressProvider.setAddress(memcacheConfigurationProperties.getServers());
//        return defaultAddressProvider;
//    }

//
//    @Bean
//    @ConditionalOnMissingBean
//    public CacheConfiguration cacheConfiguration() {
//        LOGGER.info("creating CacheConfiguration for memcache");
//        CacheConfiguration cacheConfiguration = new CacheConfiguration();
//        cacheConfiguration.setConsistentHashing(true);
//        cacheConfiguration.setUseBinaryProtocol(memcacheConfigurationProperties.isBinary());
//        return cacheConfiguration;
//    }

//    @Bean
//    @ConditionalOnMissingBean
//    public MemcacheClientFactoryImpl memcacheClientFactoryImpl() {
//        return new MemcacheClientFactoryImpl();
//    }


//    @DependsOn({"cacheBase", "memcacheClientFactoryImpl", "defaultAddressProvider", "cacheConfiguration"})
//    @Bean(value = "cacheFactory")
//    public CacheFactory cacheFactory() {
//        LOGGER.info("creating CacheFactory for memcache");
//        CacheFactory cacheFactory = new CacheFactory();
//        cacheFactory.setCacheName(memcacheConfigurationProperties.getName());
//        cacheFactory.setCacheClientFactory(memcacheClientFactoryImpl);
//        cacheFactory.setAddressProvider(defaultAddressProvider);
//        cacheFactory.setConfiguration(cacheConfiguration);
//        return cacheFactory;
//    }
//
//    @Autowired
//    private CacheFactory cacheFactory;


    @Bean
//    @DependsOn({"cacheBase", "memcacheClientFactoryImpl", "defaultAddressProvider", "cacheConfiguration"})
//    @DependsOn({"cacheBase"})
    @ConditionalOnMissingBean
    public SSMCache ssmCache() throws Exception {
        LOGGER.info("creating CacheFactory for memcache");
        DefaultAddressProvider defaultAddressProvider = new DefaultAddressProvider();
        defaultAddressProvider.setAddress(memcacheConfigurationProperties.getServers());
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setConsistentHashing(true);
        cacheConfiguration.setUseBinaryProtocol(memcacheConfigurationProperties.isBinary());

        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName(memcacheConfigurationProperties.getName());
//        cacheFactory.setCacheClientFactory(memcacheClientFactoryImpl);
        cacheFactory.setCacheClientFactory(new MemcacheClientFactoryImpl());
        cacheFactory.setAddressProvider(defaultAddressProvider);
        cacheFactory.setConfiguration(cacheConfiguration);

        LOGGER.info("creating SSMCache for memcache with ttl " + memcacheConfigurationProperties.getTtl());
        return new SSMCache(cacheFactory.getObject(), memcacheConfigurationProperties.getTtl(), false);
    }

    @Bean
//    @DependsOn("ssmCache")
    @ConditionalOnMissingBean
    public SSMCacheManager ssmCacheManager() {
        SSMCacheManager ssmCacheManager = new SSMCacheManager();
        Set<SSMCache> ssmCacheSet = new HashSet<>();
        ssmCacheSet.add(ssmCache);
        ssmCacheManager.setCaches(ssmCacheSet);
        return ssmCacheManager;
    }

}

