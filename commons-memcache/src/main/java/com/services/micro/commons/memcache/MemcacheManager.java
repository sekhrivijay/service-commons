package com.services.micro.commons.memcache;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class MemcacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcacheManager.class);

    private MemcachedClient memcachedClient;
    @Value("${service.memcache.default.ttl:6000}")
    private int ttl = 3600;

    public MemcacheManager(MemcachedClient memcachedClient, int ttl) {
        this.memcachedClient = memcachedClient;
        this.ttl = ttl;
    }

    public MemcacheManager(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void remove(String key) {
        if (memcachedClient != null) {
            try {
                memcachedClient.delete(key);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public void put(String key, int ttl, Object value) {
        if (memcachedClient != null && value != null) {
            try {
                memcachedClient.set(key, ttl, value);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public Object get(String key) {
        if (memcachedClient != null) {
            try {
                return memcachedClient.get(key);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return null;
    }

    public void shutdown() {
        memcachedClient.shutdown();
    }

    public <T> T cached(String key, Supplier<T> e, boolean isCheckable, Predicate<T> isCachable) {
        return cached(key, ttl, e, isCheckable, isCachable);
    }

    public <T> T cached(String key, Supplier<T> e, Predicate<T> isCachable) {
        return cached(key, ttl, e, true, isCachable);
    }

    public <T> T cached(String key, Supplier<T> e ) {
        return cached(key, ttl, e, true, (t) -> true);
    }

    public <T> T cached(String key, Supplier<T> e, boolean isCheckable) {
        return cached(key, ttl, e, isCheckable, (t) -> true);
    }

    public <T> T cached(String key, int ttl, Supplier<T> e, boolean isCheckable) {
        return cached(key, ttl, e, isCheckable,  (t) -> true);
    }

    public <T> T cached(String key, int ttl, Supplier<T> e) {
        return cached(key, ttl, e, true,  (t) -> true);
    }

    public <T extends Object> T cached(String key, int ttl, Supplier<T> e, boolean isCheckable, Predicate<T> isCachable) {
        if (isCheckable) {
            T cacheValue = (T) get(key);
            if (cacheValue != null) {
                return cacheValue;
            }
        }

        T value = e.get();
        if (value != null && isCachable.test(value)) {
            put(key, ttl, value);
        }
        return value;
    }
}
