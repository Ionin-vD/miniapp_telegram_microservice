package com.internal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internal.model.SelectedSchedule;

public interface SelectedScheduleRepository extends JpaRepository<SelectedSchedule, Long> {

    Optional<SelectedSchedule> findBySchedule_Id(Long scheduleId);

}
