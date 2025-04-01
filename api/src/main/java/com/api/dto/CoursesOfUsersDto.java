package com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class CoursesOfUsersDto {
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("course_id")
    private Long courseId;
    @JsonProperty("auth_in_course")
    private Boolean authInCourse;
    private UserDto user;

    public CoursesOfUsersDto(Boolean authInCourse) {
        this.authInCourse = authInCourse;
    }

    public CoursesOfUsersDto(Long id, Long usedId, Long courseId, Boolean authInCourse) {
        this.id = id;
        this.userId = usedId;
        this.courseId = courseId;
        this.authInCourse = authInCourse;
    }

    public CoursesOfUsersDto(Long id, Long usedId, Long courseId, Boolean authInCourse, String fio, Boolean isAdmin,
            Boolean isAuth) {
        this.id = id;
        this.userId = usedId;
        this.courseId = courseId;
        this.authInCourse = authInCourse;
        this.user = new UserDto(fio, isAdmin, isAuth);
    }

    @Data
    public static class UserDto {
        private String fio;
        private Boolean isAdmin;
        private Boolean isAuth;

        public UserDto(String fio, Boolean isAdmin, Boolean isAuth) {
            this.fio = fio;
            this.isAdmin = isAdmin;
            this.isAuth = isAuth;
        }
    }
}
