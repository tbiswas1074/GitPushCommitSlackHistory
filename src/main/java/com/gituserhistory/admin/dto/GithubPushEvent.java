package com.gituserhistory.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class GithubPushEvent {

    private Pusher pusher;
    private List<CommitDTO> commits;
}
