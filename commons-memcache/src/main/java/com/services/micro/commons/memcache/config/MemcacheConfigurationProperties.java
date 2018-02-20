package com.services.micro.commons.memcache.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "service.memcache")
@EnableConfigurationProperties
public class MemcacheConfigurationProperties {


    private boolean enabled;
    private List<Config> configs;


    public static class Config {
        private String servers = "localhost:11211";
        private int ttl = 3600;
        private String name = "default";
        private boolean binary = true;

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isBinary() {
            return binary;
        }

        public void setBinary(boolean binary) {
            this.binary = binary;
        }

        public String getServers() {
            return servers;
        }

        public void setServers(String servers) {
            this.servers = servers;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "ttl=" + ttl +
                    ", name='" + name + '\'' +
                    ", binary=" + binary +
                    '}';
        }
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }

//    private final static String DEFAULT_SERVERS =  "localhost:11211";
//    private final static int DEFAULT_TTL = 3600;
//    private String servers ;
//    private int ttl = 3600;
//    private String name = "default";
//    private boolean binary = true;
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public String getServers() {
////        if(servers == null) {
////            return DEFAULT_SERVERS;
////        }
//        return servers;
//    }
//
//    public void setServers(String servers) {
//        this.servers = servers;
//    }
//
//    public int getTtl() {
////        if (ttl == 0) {
////            return DEFAULT_TTL;
////        }
//        return ttl;
//    }
//
//    public void setTtl(int ttl) {
//        this.ttl = ttl;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public boolean isBinary() {
//        return binary;
//    }
//
//    public void setBinary(boolean binary) {
//        this.binary = binary;
//    }

}
