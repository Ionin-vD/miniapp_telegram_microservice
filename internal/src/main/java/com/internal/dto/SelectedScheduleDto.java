package com.internal.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SelectedScheduleDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("theme_id")
    private Long themeId;
    @JsonProperty("schedule")
    private ScheduleDto schedule;
    @JsonProperty("user")
    private UserDto user;
    @JsonProperty("theme")
    private ThemeDto theme;
    private Integer offset;

    public SelectedScheduleDto() {
    }

    public SelectedScheduleDto(Long scheduleId) {
        this.id = scheduleId;
    }

    public SelectedScheduleDto(Long id, Integer offset) {
        this.id = id;
        this.offset = offset;
    }

    public SelectedScheduleDto(Long scheduleId, Long userId, Long themeId, Long id, Long course_id, LocalDate date,
            LocalTime time) {
        this.id = scheduleId;
        this.userId = userId;
        this.themeId = themeId;
        this.schedule = new ScheduleDto(id, course_id, date, time);
    }

    public SelectedScheduleDto(Long id, Long userId, Long themeId, Long scheduleId, Long course_id, LocalDate date,
            LocalTime time, String fio, String themeTitle) {
        this.id = id;
        this.userId = userId;
        this.themeId = themeId;
        this.schedule = new ScheduleDto(scheduleId, course_id, date, time);
        this.user = new UserDto(userId, fio);
        this.theme = new ThemeDto(themeId, themeTitle);
    }

    @Data
    public static class ScheduleDto {
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

    @Data
    public static class UserDto {
        private Long id;
        private String fio;

        public UserDto() {
        }

        public UserDto(Long id) {
            this.id = id;
        }

        public UserDto(Long id, String fio) {
            this.id = id;
            this.fio = fio;
        }
    }

    @Data
    public static class ThemeDto {
        private Long id;
        private String title;

        public ThemeDto() {
        }

        public ThemeDto(Long id) {
            this.id = id;
        }

        public ThemeDto(Long id, String title) {
            this.id = id;
            this.title = title;
        }

    }
}
