package com.internal.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.internal.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
        @Query("SELECT s FROM Schedule s WHERE s.id NOT IN (SELECT ss.schedule.id FROM SelectedSchedule ss)")
        List<Schedule> findAllWhereNotSelected();

        @Query("SELECT s FROM Schedule s WHERE s.courseId = :courseId AND s.date BETWEEN :startDate AND :endDate")
        List<Schedule> findWeeklySchedule(@Param("courseId") Long courseId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        Optional<Schedule> findByCourseIdAndDateAndTime(Long course_id, LocalDate date, LocalTime time);

        List<Schedule> findAllByCourseIdAndDate(Long courseId, LocalDate date);

        boolean existsByCourseIdAndDateAndTime(Long courseId, LocalDate date, LocalTime time);

        List<Schedule> findByCourseId(Long course_id);

        List<Schedule> findAllWhereNotSelectedByCourseId(Long course_id);

        // List<Schedule> findAvailableSchedulesByCourseIdInRange(Long course_id,
        // LocalDate monday, LocalDate sunday);

        // List<Schedule> findAllInRange(LocalDate monday, LocalDate sunday);

        // List<Schedule> findAvailableSchedulesInRange(LocalDate monday, LocalDate
        // sunday);
        @Query("SELECT s FROM Schedule s WHERE s.date BETWEEN :monday AND :sunday")
        List<Schedule> findAllInRange(@Param("monday") LocalDate monday, @Param("sunday") LocalDate sunday);

        @Query("SELECT s FROM Schedule s WHERE s.id NOT IN (SELECT ss.schedule.id FROM SelectedSchedule ss) AND s.date BETWEEN :monday AND :sunday")
        List<Schedule> findAvailableSchedulesInRange(@Param("monday") LocalDate monday,
                        @Param("sunday") LocalDate sunday);

        @Query("SELECT s FROM Schedule s WHERE s.courseId = :courseId AND s.id NOT IN (SELECT ss.schedule.id FROM SelectedSchedule ss) AND s.date BETWEEN :monday AND :sunday")
        List<Schedule> findAvailableSchedulesByCourseIdInRange(@Param("courseId") Long courseId,
                        @Param("monday") LocalDate monday, @Param("sunday") LocalDate sunday);

        List<Schedule> findByCourseIdAndDateBetween(Long courseId, LocalDate startDate, LocalDate endDate);

}
