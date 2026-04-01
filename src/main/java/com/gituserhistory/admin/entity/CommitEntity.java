package com.gituserhistory.admin.entity;

import lombok.Data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commitId;
    private String message;
    private OffsetDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
