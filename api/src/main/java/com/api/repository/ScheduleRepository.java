package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
