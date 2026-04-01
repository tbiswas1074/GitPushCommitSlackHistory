package com.gituserhistory.admin.controller;


import com.gituserhistory.admin.dto.GithubPushEvent;
import com.gituserhistory.admin.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/github")
@RequiredArgsConstructor
public class GithubWebhookController {

    private final GithubService githubService;

    @PostMapping
    public ResponseEntity<Void> handlePush(
            @RequestHeader("X-GitHub-Event") String eventType,
            @RequestBody GithubPushEvent event) {

        if (!"push".equals(eventType)) {
            return ResponseEntity.ok().build();
        }

        githubService.processPushEvent(event);
        return ResponseEntity.ok().build();
    }
}
