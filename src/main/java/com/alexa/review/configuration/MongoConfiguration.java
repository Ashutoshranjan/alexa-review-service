package com.alexa.review.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

//@Configuration
public class MongoConfiguration {
/*
    @Autowired
    private Environment env;

    @Bean
    public MongoClientFactoryBean mongo(@Value("${connection.string}") String uri) throws Exception {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        ConnectionString conn = new ConnectionString(uri);
        mongo.setConnectionString(conn);

        MongoClient client = mongo.getObject();
        client.listDatabaseNames()
                .forEach(System.out::println);
        return mongo;
    }
    */
}
