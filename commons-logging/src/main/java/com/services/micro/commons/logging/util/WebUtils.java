package com.services.micro.commons.logging.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebUtils {

    private HttpServletRequest httpServletRequest;

    @Autowired
    public void setRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }


    public   String getClientIp() {
        String remoteAddr = "localhost";
        if (httpServletRequest != null) {
            remoteAddr = httpServletRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = httpServletRequest.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public String getContextPath(){
        String contextPath = "/";
        if (httpServletRequest != null) {
            contextPath = httpServletRequest.getContextPath();
        }
        return contextPath;
    }

    public String getUri(){
        String uri = "/";
        if (httpServletRequest != null) {
            uri = httpServletRequest.getRequestURI();
        }
        return uri;
    }

    public String getQueryString(){
        String query = "null";
        if (httpServletRequest != null) {
            query = httpServletRequest.getQueryString();
        }
        return query;
    }


    public String getMethod(){
        String method = "GET";
        if (httpServletRequest != null) {
            method = httpServletRequest.getMethod();
        }
        return method;
    }

    public String genInfo() {
        return "#IP: " + getClientIp() + " #HttpMethod: " + getMethod() + " #Uri: " + getUri() + " #Params: " + getQueryString();
    }


}
