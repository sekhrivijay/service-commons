package com.services.micro.commons.mongo.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@ConditionalOnProperty(name = "service.mongo.enabled")
@EnableConfigurationProperties(MongoConfigurationProperties.class)
public class MongoConfiguration extends AbstractMongoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoConfiguration.class);

    @Autowired
    private MongoConfigurationProperties mongoConfigurationProperties;


    @Override
    protected String getDatabaseName() {
        return mongoConfigurationProperties.getDatabase();
    }

    @Override
    public Mongo mongo() throws Exception {
        return getMongoClient();
    }

    private MongoClient getMongoClient() {
        LOGGER.info("creating mongo connection to " + mongoConfigurationProperties.getClientUri());
        return new MongoClient(new MongoClientURI(mongoConfigurationProperties.getClientUri()));
    }

    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(getMongoClient(), getDatabaseName());
    }

    public MappingMongoConverter mappingMongoConverter() {
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    @Bean
    public GridFsTemplate gridFsTemplate() {
        LOGGER.info("creating GridFsTemplate bean");
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

}

