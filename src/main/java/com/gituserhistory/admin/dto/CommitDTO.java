package com.gituserhistory.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class CommitDTO {

    private String id;
    private String message;
    private OffsetDateTime timestamp;
    private AuthorDTO author;
}
