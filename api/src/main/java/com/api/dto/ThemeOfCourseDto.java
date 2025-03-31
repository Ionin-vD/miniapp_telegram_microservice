package com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ThemeOfCourseDto {
    private Long id;
    private CourseDto course;

    @JsonProperty("course_id")
    private Long courseId;

    private String title;

    public ThemeOfCourseDto() {
    }

    public ThemeOfCourseDto(Long id, Long courseId, String themeTitle) {
        this.id = id;
        this.title = themeTitle;
        this.courseId = courseId;
    }

    public ThemeOfCourseDto(Long id, String themeTitle, Long courseId, String courseTitle) {
        this.id = id;
        this.title = themeTitle;
        this.courseId = courseId;
        this.course = new CourseDto(courseTitle);
    }

    @Data
    public static class CourseDto {
        private String title;

        public CourseDto(String title) {
            this.title = title;
        }
    }
}
