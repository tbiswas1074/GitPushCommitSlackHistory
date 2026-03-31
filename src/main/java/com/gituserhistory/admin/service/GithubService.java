package com.gituserhistory.admin.service;

import com.gituserhistory.admin.dto.AuthorDTO;
import com.gituserhistory.admin.dto.CommitDTO;
import com.gituserhistory.admin.dto.GithubPushEvent;
import com.gituserhistory.admin.entity.Author;
import com.gituserhistory.admin.entity.CommitEntity;
import com.gituserhistory.admin.repository.AuthorRepository;
import com.gituserhistory.admin.repository.CommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Service
@RequiredArgsConstructor
public class GithubService {

    private final AuthorRepository authorRepo;
    private final CommitRepository commitRepo;
    private final SlackService slackService;

    public void processPushEvent(GithubPushEvent event) {

        String githubUser = event.getPusher().getName();

        StringBuilder summary = new StringBuilder();

        summary.append("*New GitHub Push Detected*\n\n");
        summary.append("*GitHub User:* ").append(githubUser).append("\n\n");
        summary.append("*Commits:*\n");

        for (CommitDTO commit : event.getCommits()) {

            AuthorDTO dto = commit.getAuthor();

            Author author = authorRepo
                    .findByEmail(dto.getEmail())
                    .orElseGet(() -> {
                        Author a = new Author();
                        a.setName(dto.getName());
                        a.setEmail(dto.getEmail());
                        return authorRepo.save(a);
                    });

            CommitEntity entity = new CommitEntity();
            entity.setCommitId(commit.getId());
            entity.setMessage(commit.getMessage());
            entity.setAuthor(author);

            OffsetDateTime odt = OffsetDateTime.parse(commit.getTimestamp());
            entity.setTimestamp(
                    commit.getTimestamp() != null
                            ? OffsetDateTime.parse(commit.getTimestamp()).toLocalDateTime()
                            : LocalDateTime.now()
            );

            commitRepo.save(entity);

            summary.append("• ")
                    .append(dto.getName())
                    .append(" (")
                    .append(dto.getEmail())
                    .append(")")
                    .append(" → ")
                    .append(commit.getMessage())
                    .append("\n");
        }

        slackService.sendNotification(summary.toString());
    }
}
