package com.services.micro.commons.servlet.listener;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class HealthCheckListener extends HealthCheckServlet.ContextListener {

    @Inject
    private HealthCheckRegistry healthCheckRegistry;

    @Override
    @Named
    public HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheckRegistry;
    }
}