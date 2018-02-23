package com.services.micro.commons.metric.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.inject.Named;

@ConfigurationProperties(prefix = "service.metrics")
@EnableConfigurationProperties
public class MetricConfigurationProperties {
    private Dropwizard dropwizard;
    private Prometheus prometheus;
    private Prometheus Jmx;

    public Dropwizard getDropwizard() {
        return dropwizard;
    }

    public void setDropwizard(Dropwizard dropwizard) {
        this.dropwizard = dropwizard;
    }

    public Prometheus getPrometheus() {
        return prometheus;
    }

    public void setPrometheus(Prometheus prometheus) {
        this.prometheus = prometheus;
    }

    public Prometheus getJmx() {
        return Jmx;
    }

    public void setJmx(Prometheus jmx) {
        Jmx = jmx;
    }

    public static class Dropwizard {
        private boolean enabled = true;

        private String endpoint = "/dropMetrics";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }
    }

    public static class Prometheus {
        private boolean enabled = true;
        private String endpoint = "/promMetrics";
        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }
    }

    public static class Jmx {
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

}
