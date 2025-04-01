package com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class QuestionOfThemeDto {
    private Long id;

    @JsonProperty("theme_id")
    private Long themeId;

    private String title;

    @JsonProperty("themes_of_course")
    private ThemeDto theme;

    public QuestionOfThemeDto() {
    }

    public QuestionOfThemeDto(Long id) {
        this.id = id;
    }

    public QuestionOfThemeDto(Long id, Long themeId, String title) {
        this.id = id;
        this.title = title;
        this.themeId = themeId;
    }

    public QuestionOfThemeDto(Long id, String title, Long themeId, String titleTheme) {
        this.id = id;
        this.title = title;
        this.themeId = themeId;
        this.theme = new ThemeDto(titleTheme);
    }

    @Data
    public static class ThemeDto {
        private String title;

        public ThemeDto(String title) {
            this.title = title;
        }
    }
}
