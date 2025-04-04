package com.internal.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class ScheduleDto {
    private Long id;
    private Long course_id;
    private LocalDate date;
    private LocalTime time;
    private Integer offset;

    public ScheduleDto() {
    }

    public ScheduleDto(Long id, Integer offset) {
        this.id = id;
        this.offset = offset;
    }

    public ScheduleDto(Long id, Long course_id, LocalDate date) {
        this.id = id;
        this.course_id = course_id;
        this.date = date;
    }

    public ScheduleDto(Long id, Long course_id, LocalDate date, LocalTime time) {
        this.id = id;
        this.course_id = course_id;
        this.date = date;
        this.time = time;
    }

    public ScheduleDto(Long id, Long course_id) {
        this.id = id;
        this.course_id = course_id;
    }
}
