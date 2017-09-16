package com.services.micro.commons.jsonp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
@ConditionalOnProperty(name = "service.jsonp.enabled")
@Configuration
@EnableConfigurationProperties(JsonpConfigurationProperties.class)
public class JsonpControllerAdvice extends AbstractJsonpResponseBodyAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonpControllerAdvice.class);

    public JsonpControllerAdvice() {
        super("callback");
        LOGGER.info("jsonp is enabled ");
    }

}