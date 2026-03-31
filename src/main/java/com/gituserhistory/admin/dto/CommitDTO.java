package com.gituserhistory.admin.dto;

import lombok.Data;

@Data
public class CommitDTO {

    private String id;
    private String message;
    private String timestamp;
    private AuthorDTO author;
}
