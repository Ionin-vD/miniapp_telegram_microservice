package com.internal.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internal.model.Schedule;
import com.internal.repository.ScheduleRepository;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findAvailableSchedules() {
        return scheduleRepository.findAllWhereNotSelected();
    }

    public Optional<Schedule> findByCourseIdAndDateAndTime(Long course_id, LocalDate date, LocalTime time) {
        return scheduleRepository.findByCourseIdAndDateAndTime(course_id, date, time);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAllByCourseIdAndDate(Long course_id, LocalDate date) {
        return scheduleRepository.findAllByCourseIdAndDate(course_id, date);
    }

    public boolean existsByCourseIdAndDateAndTime(Long course_id, LocalDate date, LocalTime time) {
        return scheduleRepository.existsByCourseIdAndDateAndTime(course_id, date, time);
    }

    public List<Schedule> findByCourseId(Long course_id) {
        return scheduleRepository.findByCourseId(course_id);
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }
}
