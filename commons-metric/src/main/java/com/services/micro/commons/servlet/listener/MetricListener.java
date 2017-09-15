package com.services.micro.commons.servlet.listener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MetricListener extends MetricsServlet.ContextListener {

    @Inject
    private MetricRegistry metricRegistry;

    @Override
    @Named
    public MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }


}