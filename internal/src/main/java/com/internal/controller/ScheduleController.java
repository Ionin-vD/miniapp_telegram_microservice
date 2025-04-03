package com.internal.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internal.dto.ScheduleDto;
import com.internal.dto.ScheduleRangeRequest;
import com.internal.model.Schedule;
import com.internal.service.ScheduleService;

@RestController
@RequestMapping("/api/mini_app")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/get_all_schedule_is_course")
    public ResponseEntity<?> getSchedulesByCourse(@RequestBody ScheduleDto request) {
        if (request.getCourse_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
        }

        List<Schedule> schedules = scheduleService.findByCourseId(request.getCourse_id());

        if (schedules == null || schedules.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("free schedules is null");
        }

        List<ScheduleDto> response = schedules.stream()
                .map(s -> new ScheduleDto(s.getId(), s.getCourseId(), s.getDate(), s.getTime()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete_schedule")
    public ResponseEntity<?> deleteSchedule(@RequestBody ScheduleDto request) {
        if (request.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
        }

        try {
            scheduleService.deleteById(request.getId());
            return ResponseEntity.ok("Расписание удалено (вместе с выбранными слотами)");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении расписания: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при удалении");
        }
    }

    @PostMapping("/add_schedule")
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleRangeRequest request) {
        try {
            if (request.getCourse_id() == null || request.getDate() == null || request.getTime() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            // Форматируем дату, если она пришла в формате dd.MM.yyyy
            LocalDate date;
            try {
                if (request.getDate().contains(".")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    date = LocalDate.parse(request.getDate(), formatter);
                } else {
                    date = LocalDate.parse(request.getDate());
                }
            } catch (DateTimeParseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный формат даты");
            }

            // Парсим интервал времени
            String[] timeRange = request.getTime().split("-");
            if (timeRange.length != 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный формат времени");
            }

            LocalTime startTime = LocalTime.parse(timeRange[0]);
            LocalTime endTime = LocalTime.parse(timeRange[1]);
            int intervalMinutes = 90;

            List<Schedule> existingSchedules = scheduleService.findAllByCourseIdAndDate(request.getCourse_id(), date);

            List<Schedule> createdSchedules = new ArrayList<>();

            for (LocalTime current = startTime; current
                    .isBefore(endTime); current = current.plusMinutes(intervalMinutes)) {
                final LocalTime currentSlot = current;

                boolean conflict = existingSchedules.stream()
                        .anyMatch(
                                s -> Math.abs(ChronoUnit.MINUTES.between(s.getTime(), currentSlot)) < intervalMinutes);

                if (conflict) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Конфликт расписания на " + currentSlot);
                }

                boolean alreadyExists = scheduleService.existsByCourseIdAndDateAndTime(
                        request.getCourse_id(), date, currentSlot);
                if (alreadyExists) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Расписание на " + currentSlot + " уже существует");
                }

                Schedule schedule = scheduleService.createSchedule(new Schedule(
                        request.getCourse_id(), date, currentSlot));
                createdSchedules.add(schedule);
                existingSchedules.add(schedule);
            }

            return ResponseEntity.ok("Расписание успешно добавлено: " + createdSchedules.size());
        } catch (Exception e) {
            System.err.println("Ошибка при создании расписания: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @PostMapping("/add_free_schedule")
    public ResponseEntity<?> addFreeSchedule(@RequestBody ScheduleDto request) {
        try {
            if (request.getCourse_id() == null || request.getDate() == null || request.getTime() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            Optional<Schedule> existingSchedule = scheduleService.findByCourseIdAndDateAndTime(request.getCourse_id(),
                    request.getDate(),
                    request.getTime());
            if (existingSchedule.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Расписание уже существует");
            }

            Schedule schedule = scheduleService.createSchedule(new Schedule(request.getCourse_id(), request.getDate(),
                    request.getTime()));
            if (schedule != null) {
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: расписание не создано");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при создании расписания: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @GetMapping("/get_all_schedule")
    public ResponseEntity<?> getAllSchedule() {
        try {
            List<Schedule> schedules = scheduleService.findAll();
            if (!schedules.isEmpty()) {
                List<ScheduleDto> scheduleDtos = schedules.stream()
                        .map(schedule -> new ScheduleDto(
                                schedule.getId(),
                                schedule.getCourseId(),
                                schedule.getDate(),
                                schedule.getTime()))
                        .toList();

                return ResponseEntity.ok(scheduleDtos);
            } else {
                return ResponseEntity.ok("schedule null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получение всего расписания: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @GetMapping("/get_all_free_schedule")
    public ResponseEntity<?> getAllFreeSchedule() {
        try {
            List<Schedule> schedules = scheduleService.findAvailableSchedules();
            if (!schedules.isEmpty()) {
                List<ScheduleDto> scheduleDtos = schedules.stream()
                        .map(schedule -> new ScheduleDto(
                                schedule.getId(),
                                schedule.getCourseId(),
                                schedule.getDate(),
                                schedule.getTime()))
                        .toList();

                return ResponseEntity.ok(scheduleDtos);
            } else {
                return ResponseEntity.ok("schedule null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получение всего расписания: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }
}
