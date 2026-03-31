package com.gituserhistory.admin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SlackService {

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendNotification(String message) {

        Map<String, String> body = new HashMap<>();
        body.put("text", "*New GitHub Push Detected*\n" + message);

        restTemplate.postForObject(webhookUrl, body, String.class);
    }
}