package com.gituserhistory.admin.controller;


import com.gituserhistory.admin.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhook/github")
@RequiredArgsConstructor
public class GithubWebhookController {

    private final GithubService githubService;


    @PostMapping
    public ResponseEntity<Void> handlePush(
            @RequestBody Map<String, Object> payload) {

        githubService.processPushEvent(payload);
        return ResponseEntity.ok().build();
    }

}
