package com.services.micro.commons.metric.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "service.metrics")
@EnableConfigurationProperties
public class MetricConfigurationProperties {
    private Dropwizard dropwizard;
    private Prometheus prometheus;
    private Jmx jmx;
    private Performance performance;

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

    public Jmx getJmx() {
        return jmx;
    }

    public void setJmx(Jmx jmx) {
        this.jmx = jmx;
    }



    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public static class Performance {
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
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
