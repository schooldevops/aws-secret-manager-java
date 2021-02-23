package com.schooldevops.secret.testsecret;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Properties;

@Component
public class PropertyListener implements ApplicationListener<ApplicationPreparedEvent> {

    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyListener.class);
    private static final String AWS_SECRET_NAME = "myproject/schooldevops/db";

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        String secretJson = getSecretV1();

        ConfigurableEnvironment env = event.getApplicationContext().getEnvironment();
        Properties properties = new Properties();

        properties.put("myproject.schooldevops.db.username", getValue(secretJson, "username"));
        properties.put("myproject.schooldevops.db.password", getValue(secretJson, "password"));
        properties.put("myproject.schooldevops.db.token", getValue(secretJson, "usertoken"));

        env.getPropertySources().addFirst(new PropertiesPropertySource("myproject.schooldevops.db", properties));
    }

    private String getSecretV1() {
        // Create a Secrets Manager client
        AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        String secret, decodedBinarySecret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(AWS_SECRET_NAME);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            return secret;
        }
        else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
            return decodedBinarySecret;
        }
    }

    private String getValue(String json, String key) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return jsonNode.path(key).asText();
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}