package com.gituserhistory.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommitDTO {

    private String id;
    private String message;
    private LocalDateTime timestamp;
    private AuthorDTO author;
}
