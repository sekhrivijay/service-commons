package com.services.micro.commons.git.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ConditionalOnProperty(name = "service.git.enabled")
@EnableConfigurationProperties(GitConfigurationProperties.class)
public class GitConfiguration {
    public static final String GIT_PROPERTIES = "git.properties";

    @Bean
    public static PropertySourcesPlaceholderConfigurer gitPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setLocation(new ClassPathResource(GIT_PROPERTIES));
        c.setIgnoreResourceNotFound(true);
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }
}
