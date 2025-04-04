package com.internal.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.internal.model.SelectedSchedule;

public interface SelectedScheduleRepository extends JpaRepository<SelectedSchedule, Long> {

    List<SelectedSchedule> findBySchedule_Id(Long scheduleId);

    List<SelectedSchedule> findAllByUserId(Long userId);

    List<SelectedSchedule> findByScheduleIdIn(List<Long> scheduleIds);

    @Query("SELECT ss FROM SelectedSchedule ss WHERE ss.userId = :userId AND ss.schedule.date BETWEEN :startDate AND :endDate")
    List<SelectedSchedule> findAllByUserIdAndDateRange(@Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
