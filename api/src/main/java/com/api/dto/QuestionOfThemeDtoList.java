package com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class QuestionOfThemeDtoList {

    @JsonProperty("id")
    private List<Long> ids;

    @JsonProperty("theme_id")
    private Long themeId;

    @JsonProperty("title")
    private List<String> titleList;

    @JsonProperty("themes_of_course")
    private ThemeDto theme;

    public QuestionOfThemeDtoList() {
    }

    @Data
    public static class ThemeDto {
        private String title;

        public ThemeDto(String title) {
            this.title = title;
        }
    }
}
