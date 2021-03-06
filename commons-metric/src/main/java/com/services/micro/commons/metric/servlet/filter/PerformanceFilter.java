package com.services.micro.commons.metric.servlet.filter;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

//@WebFilter
public class PerformanceFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceFilter.class.getName());
    public static final String PERFORMANCE = "performance";
    public static final String REQUEST_COUNT = "request_count";
    public static final String AVG_RESPONSE_TIME = "avg_response_time";
    public static final String REQUEST_RATE = "requests_per_minute";
    private final String TIMER_NAME;

    private MetricRegistry metricRegistry;

    private String environment;
    private String applicationName;


    private long requestCount;

    private double avgResponseTime;

    private double requestRate;


    public PerformanceFilter(MetricRegistry metricRegistry, String environment, String applicationName) {
        this.metricRegistry = metricRegistry;
        this.environment = environment;
        this.applicationName = applicationName;
        TIMER_NAME = applicationName + "." + environment + "." + PERFORMANCE;
        if (metricRegistry != null) {
//            metricRegistry.register(MetricRegistry.name(TIMER_NAME, REQUEST_COUNT), (Gauge<Long>) () -> requestCount);
//            metricRegistry.register(MetricRegistry.name(TIMER_NAME, AVG_RESPONSE_TIME), (Gauge<Double>) () -> avgResponseTime);
//            metricRegistry.register(MetricRegistry.name(TIMER_NAME, REQUEST_RATE), (Gauge<Double>) () -> requestRate);
//
            metricRegistry.register(MetricRegistry.name(REQUEST_COUNT), (Gauge<Long>) () -> requestCount);
            metricRegistry.register(MetricRegistry.name(AVG_RESPONSE_TIME), (Gauge<Double>) () -> avgResponseTime);
            metricRegistry.register(MetricRegistry.name(REQUEST_RATE), (Gauge<Double>) () -> requestRate);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        updateMetrics();
//        System.out.println("INSIDE filterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" + PerformanceFilter.class.getName());
        if (metricRegistry == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Timer.Context timerContext = null;
        Timer timer = null;
        try {
            try {
                timer = metricRegistry.timer(TIMER_NAME);
                if (timer != null) {
                    timerContext = timer.time();
                }
            } catch (Exception e) {
                LOGGER.error("Performance filter issue", e);
            }
            filterChain.doFilter(servletRequest, servletResponse);
            updateGauges(timer);
        } catch (Exception ex) {
            LOGGER.error("Performance filter caught this error ", ex);
            throw ex;
        } finally {
            if (timerContext != null) {
                timerContext.stop();
            }
        }
    }

    private void updateGauges(Timer timer) {
        try {
            if (timer != null) {
                Snapshot snapshot = timer.getSnapshot();
                requestCount = timer.getCount();
                requestRate = timer.getOneMinuteRate() * 60;
                if (snapshot != null) {
                    avgResponseTime = snapshot.getMean() / 1000000;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Performance filter issue", e);
        }
    }

    @Override
    public void destroy() {

    }
}
