package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
}
