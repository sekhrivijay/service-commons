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
import java.util.List;
import java.util.Set;

@Configuration
@EnableCaching
//@ImportResource({"classpath*:memcache-context.xml"})
@ConditionalOnProperty(name = "service.memcache.enabled")
@EnableConfigurationProperties(MemcacheConfigurationProperties.class)
public class MemcacheConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcacheConfiguration.class);


    private MemcacheConfigurationProperties memcacheConfigurationProperties;

    @Autowired
    public void setMemcacheConfigurationProperties(MemcacheConfigurationProperties memcacheConfigurationProperties) {
        this.memcacheConfigurationProperties = memcacheConfigurationProperties;
    }

    private CacheBase cacheBase;

    @Autowired
    public void setCacheBase(CacheBase cacheBase) {
        this.cacheBase = cacheBase;
    }

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



    public SSMCache buildSsmCache(MemcacheConfigurationProperties.Config config)  {
        LOGGER.info("creating CacheFactory for memcache");
        DefaultAddressProvider defaultAddressProvider = new DefaultAddressProvider();
        defaultAddressProvider.setAddress(memcacheConfigurationProperties.getServers());
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setConsistentHashing(true);
        cacheConfiguration.setUseBinaryProtocol(config.isBinary());

        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName(config.getName());
        cacheFactory.setCacheClientFactory(new MemcacheClientFactoryImpl());
        cacheFactory.setAddressProvider(defaultAddressProvider);
        cacheFactory.setConfiguration(cacheConfiguration);
        SSMCache ssmCache = null;
        LOGGER.info("creating SSMCache " + config.getName() + " for memcache with ttl " + config.getTtl());
        try {
            ssmCache = new SSMCache(cacheFactory.getObject(), config.getTtl(), false);
        } catch (Exception e) {
            LOGGER.error("Could not initialize cache ", e);
        }
        return ssmCache;
    }

    @Bean
    @ConditionalOnMissingBean
    public SSMCacheManager ssmCacheManager() throws Exception {
        SSMCacheManager ssmCacheManager = new SSMCacheManager();
        Set<SSMCache> ssmCacheSet = new HashSet<>();
        List<MemcacheConfigurationProperties.Config> configs = memcacheConfigurationProperties.getConfigs();
        if(configs != null && configs.size() > 0) {
            configs.forEach(e -> ssmCacheSet.add(buildSsmCache(e)));
        }
        ssmCacheManager.setCaches(ssmCacheSet);
        return ssmCacheManager;
    }

}

