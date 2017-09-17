package com.services.micro.commons.git.resource;

import com.services.micro.commons.git.config.GitConfiguration;
import com.services.micro.commons.git.util.PropertiesLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@ConditionalOnProperty(name = "service.git.enabled")
public class GitResource {

    private Properties properties;

    public GitResource() {
        try {
            properties = new Properties();
            PropertiesLoader.load(properties, GitConfiguration.GIT_PROPERTIES);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/buildStatus")
    public Properties getGitInfo() {
        return properties;
    }
}