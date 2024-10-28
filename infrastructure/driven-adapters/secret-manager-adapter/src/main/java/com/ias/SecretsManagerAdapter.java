package com.ias;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Component
@RequiredArgsConstructor
public class SecretsManagerAdapter {

    @Value("${aws.secrets.rabbitmq-secret-name}")
    private String secretName;

    private final SecretsManagerClient secretsManagerClient;


    public String getRabbitMQSecret() {
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = secretsManagerClient.getSecretValue(request);
        return response.secretString();
    }
}
