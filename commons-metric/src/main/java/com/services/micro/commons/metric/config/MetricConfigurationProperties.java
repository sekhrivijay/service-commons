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

    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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

    public static class Dropwizard {
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Prometheus {
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

}
