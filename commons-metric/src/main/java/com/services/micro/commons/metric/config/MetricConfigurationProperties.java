package com.services.micro.commons.metric.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "service.metrics")
@EnableConfigurationProperties
public class MetricConfigurationProperties {

    private Dropwizard dropwizard;
    private Prometheous prometheous;


    public Dropwizard getDropwizard() {
        return dropwizard;
    }

    public void setDropwizard(Dropwizard dropwizard) {
        this.dropwizard = dropwizard;
    }

    public Prometheous getPrometheous() {
        return prometheous;
    }

    public void setPrometheous(Prometheous prometheous) {
        this.prometheous = prometheous;
    }

    private static class Dropwizard {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    private static class Prometheous {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }


}
