package com.gituserhistory.admin.service;

import com.gituserhistory.admin.entity.Author;
import com.gituserhistory.admin.entity.CommitEntity;
import com.gituserhistory.admin.repository.AuthorRepository;
import com.gituserhistory.admin.repository.CommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final AuthorRepository authorRepo;
    private final CommitRepository commitRepo;
    private final SlackService slackService;

    public void processPushEvent(Map<String, Object> payload) {

        List<Map<String, Object>> commits =
                (List<Map<String, Object>>) payload.get("commits");

        StringBuilder summary = new StringBuilder();

        for (Map<String, Object> commit : commits) {

            Map<String, String> authorMap =
                    (Map<String, String>) commit.get("author");

            String email = authorMap.get("email");

            Author author = authorRepo
                    .findByEmail(email)
                    .orElseGet(() -> {
                        Author a = new Author();
                        a.setName(authorMap.get("name"));
                        a.setEmail(email);
                        return authorRepo.save(a);
                    });

            CommitEntity entity = new CommitEntity();
            entity.setCommitId((String) commit.get("id"));
            entity.setMessage((String) commit.get("message"));
            entity.setTimestamp((String) commit.get("timestamp"));
            entity.setAuthor(author);

            commitRepo.save(entity);

            summary.append("• ")
                    .append(author.getName())
                    .append(": ")
                    .append(entity.getMessage())
                    .append("\n");
        }

        slackService.sendNotification(summary.toString());
    }
}
