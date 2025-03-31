package com.internal.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleDto {
    private Long id;
    private Long course_id;
    private LocalDate date;
    private LocalTime time;

    public ScheduleDto() {
    }

    public ScheduleDto(Long id, Long course_id, LocalDate date) {
        this.id = id;
        this.course_id = course_id;
        this.date = date;
    }

    public ScheduleDto(Long id, Long course_id) {
        this.id = id;
        this.course_id = course_id;
    }
}
