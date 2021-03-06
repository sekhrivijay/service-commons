package com.services.micro.commons.metric.config;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.AdminServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import com.services.micro.commons.metric.servlet.filter.PerformanceFilter;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;

@Configuration
@EnableMetrics
@EnableConfigurationProperties(MetricConfigurationProperties.class)
public class MetricConfiguration extends MetricsConfigurerAdapter {


    private static final Logger LOGGER = LoggerFactory.getLogger(MetricConfiguration.class);
    private CollectorRegistry collectorRegistry;
    private MetricRegistry metricRegistry;

    @Value("${spring.application.name:default}")
    public String applicationName;

    @Value("${spring.profiles.active:local}")
    public String environment;

    private MetricConfigurationProperties metricConfigurationProperties;

    public MetricConfigurationProperties getMetricConfigurationProperties() {
        return metricConfigurationProperties;
    }

    @Autowired
    public void setMetricConfigurationProperties(MetricConfigurationProperties metricConfigurationProperties) {
        this.metricConfigurationProperties = metricConfigurationProperties;
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {

    }


    @Autowired
    public void setCollectorRegistry(CollectorRegistry collectorRegistry) {
        this.collectorRegistry = collectorRegistry;
    }

    @Autowired
    public void setMetricRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Bean
    public CollectorRegistry collectorRegistry() {
        return new CollectorRegistry();
    }


    @Bean
    @ConditionalOnProperty(name = "service.metrics.dropwizard.enabled")
    public ServletRegistrationBean adminServletRegistrationBean() {
        LOGGER.info("creating dropwizard metrics endpoint");
//        return new ServletRegistrationBean(new AdminServlet(), "/dropMetrics/*");
        return new ServletRegistrationBean(new AdminServlet(), metricConfigurationProperties.getDropwizard().getEndpoint() + "/*");
    }

    @PostConstruct
    public void startJmx() {
        if (metricConfigurationProperties.getJmx() != null
                && metricConfigurationProperties.getJmx().isEnabled()) {
            LOGGER.info("Starting JMX reporter ....");
            registerReporter(JmxReporter.forRegistry(metricRegistry).build()).start();
        }
    }


    @Bean
    @DependsOn("collectorRegistry")
    @ConditionalOnProperty(name = "service.metrics.prometheus.enabled")
    public ServletRegistrationBean prometheusServletRegistrationBean() {
        LOGGER.info("creating prometheus metrics endpoint");
        collectorRegistry.register(new DropwizardExports(metricRegistry));
        MetricsServlet metricsServlet = new MetricsServlet(collectorRegistry);
        return new ServletRegistrationBean(metricsServlet, metricConfigurationProperties.getPrometheus().getEndpoint());
    }



    @Bean
    @ConditionalOnProperty(name = "service.metrics.performance.enabled")
    public FilterRegistrationBean registration() {
        LOGGER.info("Performance filter started ....");
        FilterRegistrationBean registration = new FilterRegistrationBean(
                new PerformanceFilter(metricRegistry, environment, applicationName));
        registration.setEnabled(true);
//        registration.setUrlPatterns(Arrays.asList("/*"));
        return registration;
    }
}
