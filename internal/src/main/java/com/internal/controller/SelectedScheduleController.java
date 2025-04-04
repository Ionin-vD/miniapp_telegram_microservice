package com.internal.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.internal.dto.CourseIdRequest;
import com.internal.dto.SelectedScheduleDto;
import com.internal.model.Schedule;
import com.internal.model.SelectedSchedule;
import com.internal.service.ScheduleService;
import com.internal.service.SelectedScheduleService;

@RestController
@RequestMapping("/api/mini_app")
public class SelectedScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private SelectedScheduleService selectedScheduleService;

    @Autowired
    private WebClient webClient;

    @PostMapping("/get_all_course_selected_schedule")
    public ResponseEntity<?> getAllCourseSelectedSchedule(@RequestBody CourseIdRequest request) {
        try {
            if (request.getCourse_id() == null || request.getOffset() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
            }

            LocalDate monday = LocalDate.now().plusWeeks(request.getOffset()).with(DayOfWeek.MONDAY);
            LocalDate sunday = LocalDate.now().plusWeeks(request.getOffset()).with(DayOfWeek.SUNDAY);

            List<Schedule> scheduleList = scheduleService.findByCourseIdAndDateBetween(request.getCourse_id(), monday,
                    sunday);
            if (scheduleList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("schedules not found for course");
            }

            List<Long> scheduleIds = scheduleList.stream()
                    .map(Schedule::getId)
                    .toList();

            List<SelectedSchedule> selectedSchedules = selectedScheduleService.findByScheduleIdIn(scheduleIds);
            if (selectedSchedules.isEmpty()) {
                return ResponseEntity.ok("selected schedules is null");
            }

            Set<Long> userIds = selectedSchedules.stream().map(SelectedSchedule::getUserId).collect(Collectors.toSet());
            Set<Long> themeIds = selectedSchedules.stream().map(SelectedSchedule::getThemeId)
                    .collect(Collectors.toSet());

            Map<Long, String> userFioMap = webClient.post()
                    .uri("api/mini_app/find_users_fio_by_ids")
                    .bodyValue(Map.of("ids", userIds))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<Long, String>>() {
                    })
                    .block();

            Map<Long, String> themeTitleMap = webClient.post()
                    .uri("api/mini_app/find_themes_title_by_ids")
                    .bodyValue(Map.of("ids", themeIds))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<Long, String>>() {
                    })
                    .block();

            List<SelectedScheduleDto> response = selectedSchedules.stream()
                    .map(s -> new SelectedScheduleDto(
                            s.getId(),
                            s.getUserId(),
                            s.getThemeId(),
                            s.getSchedule().getId(),
                            s.getSchedule().getCourseId(),
                            s.getSchedule().getDate(),
                            s.getSchedule().getTime(),
                            userFioMap.getOrDefault(s.getUserId(), "Unknown"),
                            themeTitleMap.getOrDefault(s.getThemeId(), "Unknown")))
                    .toList();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Ошибка при получение всего расписания для курса: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/get_all_user_selected_schedule")
    public ResponseEntity<?> getAllUserSelectedSchedule(@RequestBody SelectedScheduleDto request) {
        try {
            if (request.getUserId() == null || request.getOffset() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
            }

            LocalDate monday = LocalDate.now().plusWeeks(request.getOffset()).with(DayOfWeek.MONDAY);
            LocalDate sunday = LocalDate.now().plusWeeks(request.getOffset()).with(DayOfWeek.SUNDAY);

            List<SelectedSchedule> schedules = selectedScheduleService.findAllByUserIdAndDateRange(request.getUserId(),
                    monday, sunday);
            if (!schedules.isEmpty()) {
                List<SelectedScheduleDto> scheduleDtos = schedules.stream()
                        .map(schedule -> new SelectedScheduleDto(
                                schedule.getId(),
                                schedule.getUserId(),
                                schedule.getThemeId(),
                                schedule.getSchedule().getId(),
                                schedule.getSchedule().getCourseId(),
                                schedule.getSchedule().getDate(),
                                schedule.getSchedule().getTime()))
                        .toList();

                return ResponseEntity.ok(scheduleDtos);
            } else {
                return ResponseEntity.ok("schedule null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получение всего расписания выбранным пользователем: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    /*
     * @PostMapping("/get_all_course_selected_schedule")
     * public ResponseEntity<?> getAllCourseSelectedSchedule(@RequestBody
     * CourseIdRequest request) {
     * try {
     * if (request.getCourse_id() == null) {
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
     * }
     * 
     * List<Schedule> scheduleList =
     * scheduleService.findByCourseId(request.getCourse_id());
     * if (scheduleList.isEmpty()) {
     * return ResponseEntity.status(HttpStatus.NOT_FOUND).
     * body("schedules not found for course");
     * }
     * 
     * List<Long> scheduleIds = scheduleList.stream()
     * .map(Schedule::getId)
     * .toList();
     * 
     * List<SelectedSchedule> selectedSchedules =
     * selectedScheduleService.findByScheduleIdIn(scheduleIds);
     * if (selectedSchedules.isEmpty()) {
     * return ResponseEntity.ok("selected schedules is null");
     * }
     * 
     * Set<Long> userIds =
     * selectedSchedules.stream().map(SelectedSchedule::getUserId).collect(
     * Collectors.toSet());
     * Set<Long> themeIds =
     * selectedSchedules.stream().map(SelectedSchedule::getThemeId)
     * .collect(Collectors.toSet());
     * 
     * Map<Long, String> userFioMap = webClient.post()
     * .uri("api/mini_app/find_users_fio_by_ids")
     * .bodyValue(Map.of("ids", userIds))
     * .retrieve()
     * .bodyToMono(new ParameterizedTypeReference<Map<Long, String>>() {
     * })
     * .block();
     * 
     * Map<Long, String> themeTitleMap = webClient.post()
     * .uri("api/mini_app/find_themes_title_by_ids")
     * .bodyValue(Map.of("ids", themeIds))
     * .retrieve()
     * .bodyToMono(new ParameterizedTypeReference<Map<Long, String>>() {
     * })
     * .block();
     * 
     * List<SelectedScheduleDto> response = selectedSchedules.stream()
     * .map(s -> new SelectedScheduleDto(
     * s.getId(),
     * s.getUserId(),
     * s.getThemeId(),
     * s.getSchedule().getId(),
     * s.getSchedule().getCourseId(),
     * s.getSchedule().getDate(),
     * s.getSchedule().getTime(),
     * userFioMap.getOrDefault(s.getUserId(), "Unknown"),
     * themeTitleMap.getOrDefault(s.getThemeId(), "Unknown")))
     * .toList();
     * 
     * return ResponseEntity.ok(response);
     * } catch (Exception e) {
     * System.err.println("Ошибка при получение всего расписания для курса: " +
     * e.getMessage());
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
     * .body("Ошибка сервера");
     * }
     * }
     * 
     * @PostMapping("/get_all_user_selected_schedule")
     * public ResponseEntity<?> getAllUserSelectedSchedule(@RequestBody
     * SelectedScheduleDto request) {
     * try {
     * if (request.getUserId() == null) {
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
     * }
     * List<SelectedSchedule> schedules =
     * selectedScheduleService.findAllByUserId(request.getUserId());
     * if (!schedules.isEmpty()) {
     * List<SelectedScheduleDto> scheduleDtos = schedules.stream()
     * .map(schedule -> new SelectedScheduleDto(
     * schedule.getId(),
     * schedule.getUserId(),
     * schedule.getThemeId(),
     * schedule.getSchedule().getId(),
     * schedule.getSchedule().getCourseId(),
     * schedule.getSchedule().getDate(),
     * schedule.getSchedule().getTime()))
     * .toList();
     * 
     * return ResponseEntity.ok(scheduleDtos);
     * } else {
     * return ResponseEntity.ok("schedule null");
     * }
     * } catch (Exception e) {
     * System.err.
     * println("Ошибка при получение всего расписания выбранным пользователем: " +
     * e.getMessage());
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
     * .body("Ошибка сервера");
     * }
     * }
     */

    @PostMapping("/delete_user_schedule")
    public ResponseEntity<?> deleteSelectedSchedule(@RequestBody SelectedScheduleDto request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
            }

            selectedScheduleService.deleteById(request.getId());
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя из расписания: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/add_user_schedule")
    public ResponseEntity<?> selectSchedule(@RequestBody SelectedScheduleDto request) {
        try {
            if (request.getId() == null || request.getUserId() == null || request.getThemeId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
            }

            List<SelectedSchedule> exists = selectedScheduleService.findByScheduleId(request.getId());
            if (!exists.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Это время уже занято");
            }

            Schedule schedule = scheduleService.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Расписание не найдено"));

            SelectedSchedule selected = new SelectedSchedule();
            selected.setSchedule(schedule);
            selected.setUserId(request.getUserId());
            selected.setThemeId(request.getThemeId());

            SelectedSchedule saved = selectedScheduleService.save(selected);
            SelectedScheduleDto res = new SelectedScheduleDto(
                    saved.getId(),
                    saved.getUserId(),
                    saved.getThemeId(),
                    saved.getSchedule().getId(),
                    saved.getSchedule().getCourseId(),
                    saved.getSchedule().getDate(),
                    saved.getSchedule().getTime());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.err.println("Ошибка при добавление пользователя в расписания: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }
}
