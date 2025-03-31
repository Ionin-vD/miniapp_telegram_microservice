package com.internal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internal.dto.SelectedScheduleRequest;
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

    @PostMapping("/add_user_schedule")
    public ResponseEntity<?> selectSchedule(@RequestBody SelectedScheduleRequest request) {
        if (request.getScheduleId() == null || request.getUserId() == null || request.getThemeId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
        }

        Optional<SelectedSchedule> exists = selectedScheduleService.findByScheduleId(request.getScheduleId());
        if (exists.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Это время уже занято");
        }

        Schedule schedule = scheduleService.findById(request.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Расписание не найдено"));

        SelectedSchedule selected = new SelectedSchedule();
        selected.setSchedule(schedule);
        selected.setUserId(request.getUserId());
        selected.setThemeId(request.getThemeId());

        SelectedSchedule saved = selectedScheduleService.save(selected);
        return ResponseEntity.ok(saved);
    }
}
