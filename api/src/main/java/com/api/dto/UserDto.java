package com.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private Long chat_id;
    private String fio;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    @JsonProperty("isAuth")
    private boolean isAuth;
}
