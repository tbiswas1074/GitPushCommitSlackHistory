package com.gituserhistory.admin.repository;

import com.gituserhistory.admin.entity.CommitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<CommitEntity, Long> {
}
