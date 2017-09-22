package com.services.micro.commons.logging.aspect;

import com.services.micro.commons.logging.util.WebUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Aspect
@Component
@ConditionalOnProperty(name = "service.logging.enabled")
public class LogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);


    private WebUtils webUtils;

    @Autowired
    public void setWebUtils(WebUtils webUtils) {
        this.webUtils = webUtils;
    }

    @Around(value = "@annotation(com.services.micro.commons.logging.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        final Object proceed = joinPoint.proceed();
        final long executionTime = System.currentTimeMillis() - start;
        LOGGER.info(webUtils.genInfo() + " #Method: " + joinPoint.getSignature() + " #Time: " + executionTime );
        return proceed;
    }
}
