package com.internal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internal.model.SelectedSchedule;

public interface SelectedScheduleRepository extends JpaRepository<SelectedSchedule, Long> {

    List<SelectedSchedule> findBySchedule_Id(Long scheduleId);

    List<SelectedSchedule> findAllByUserId(Long userId);

    List<SelectedSchedule> findByScheduleIdIn(List<Long> scheduleIds);

}
