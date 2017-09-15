package com.services.micro.commons.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "service" )
@EnableConfigurationProperties
public class MetricConfigurationProperties {
    private Metrics metrics;

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    private static class Metrics {
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


}
