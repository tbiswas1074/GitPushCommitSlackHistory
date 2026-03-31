package com.gituserhistory.admin.controller;


import com.gituserhistory.admin.dto.GithubPushEvent;
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
public class GithubWebhookController {

    private final GithubService githubService;

    public GithubWebhookController(GithubService githubService) {
        this.githubService = githubService;
    }


    @PostMapping
    public ResponseEntity<Void> handlePush(
            @RequestBody GithubPushEvent event) {

        githubService.processPushEvent(event);
        return ResponseEntity.ok().build();
    }

}
