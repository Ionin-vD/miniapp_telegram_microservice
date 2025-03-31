package com.internal.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.internal.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE s.id NOT IN (SELECT ss.schedule.id FROM SelectedSchedule ss)")
    List<Schedule> findAllWhereNotSelected();

    Optional<Schedule> findByCourseIdAndDateAndTime(Long course_id, LocalDate date, LocalTime time);

    List<Schedule> findAllByCourseIdAndDate(Long courseId, LocalDate date);

    boolean existsByCourseIdAndDateAndTime(Long courseId, LocalDate date, LocalTime time);

    List<Schedule> findByCourseId(Long course_id);
}
