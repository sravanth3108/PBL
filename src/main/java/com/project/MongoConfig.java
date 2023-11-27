package com.project;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "pbl";
    }

    @Override
    public MongoClient mongoClient() {
        // Replace the connection string with your MongoDB Atlas connection string
        ConnectionString connectionString = new ConnectionString("mongodb+srv://sravanth:sravanth@pbl.wuhdniy.mongodb.net/?retryWrites=true&w=majority");
        return MongoClients.create(connectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}

