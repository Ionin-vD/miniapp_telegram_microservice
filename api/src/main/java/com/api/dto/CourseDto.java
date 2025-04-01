package com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CourseDto {
    private Long id;
    private AdminDto admin;

    @JsonProperty("admin_id")
    private Long adminId;

    private String title;

    public CourseDto() {
    }

    public CourseDto(Long id) {
        this.id = id;
    }

    public CourseDto(Long adminId, String title) {
        this.adminId = adminId;
        this.title = title;
    }

    public CourseDto(Long id, Long adminId, String title) {
        this.id = id;
        this.adminId = adminId;
        this.title = title;
    }

    public CourseDto(Long id, Long adminId, String fio, String title) {
        this.id = id;
        this.adminId = adminId;
        this.admin = new AdminDto(fio);
        this.title = title;
    }

    @Data
    public static class AdminDto {
        private String fio;

        public AdminDto(String fio) {
            this.fio = fio;
        }
    }

}
