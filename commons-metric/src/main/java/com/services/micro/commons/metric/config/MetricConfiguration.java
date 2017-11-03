package com.services.micro.commons.metric.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.AdminServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableMetrics
@EnableConfigurationProperties(MetricConfigurationProperties.class)
public class MetricConfiguration extends MetricsConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricConfiguration.class);

    @Autowired
    private MetricConfigurationProperties metricConfigurationProperties;

    public MetricConfigurationProperties getMetricConfigurationProperties() {
        return metricConfigurationProperties;
    }

    public void setMetricConfigurationProperties(MetricConfigurationProperties metricConfigurationProperties) {
        this.metricConfigurationProperties = metricConfigurationProperties;
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
//        registerReporter(ConsoleReporter
//                .forRegistry(metricRegistry)
//                .build())
//                .start(100, TimeUnit.SECONDS);
    }

    @Autowired
    private CollectorRegistry collectorRegistry;

    @Autowired
    private MetricRegistry metricRegistry;

    @Bean
    public CollectorRegistry collectorRegistry() {
        return new CollectorRegistry();
    }


    @Bean
    @ConditionalOnProperty(name = "service.metrics.dropwizard.enabled")
    public ServletRegistrationBean adminServletRegistrationBean() {
        LOGGER.info("creating dropwizard metrics endpoint");

        return new ServletRegistrationBean(new AdminServlet(), "/dropMetrics/*");
    }

    @Bean
    @DependsOn("collectorRegistry")
    @ConditionalOnProperty(name = "service.metrics.prometheus.enabled")
    public ServletRegistrationBean prometheusServletRegistrationBean() {
        LOGGER.info("creating prometheus metrics endpoint");
        collectorRegistry.register(new DropwizardExports(metricRegistry));
        MetricsServlet metricsServlet = new MetricsServlet(collectorRegistry);
        return new ServletRegistrationBean(metricsServlet, "/promMetrics");
    }
}
