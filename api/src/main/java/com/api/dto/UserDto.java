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
    private Boolean isAdmin;
    @JsonProperty("isAuth")
    private Boolean isAuth;
    @JsonProperty("isDeleted")
    private Boolean isDeleted;

    public UserDto() {
    }

    public UserDto(Long id, Long chat_id, String fio, boolean isAdmin, boolean isAuth) {
        this.id = id;
        this.chat_id = chat_id;
        this.fio = fio;
        this.isAdmin = isAdmin;
        this.isAuth = isAuth;
    }

    public UserDto(Long id, Long chat_id, String fio, boolean isAdmin, boolean isAuth, boolean isDeleted) {
        this.id = id;
        this.chat_id = chat_id;
        this.fio = fio;
        this.isAdmin = isAdmin;
        this.isAuth = isAuth;
        this.isDeleted = isDeleted;
    }

    public UserDto(Long id, Long chat_id, String fio, boolean isAdmin) {
        this.id = id;
        this.chat_id = chat_id;
        this.fio = fio;
        this.isAdmin = isAdmin;
    }

    public UserDto(Long id, Long chat_id, String fio) {
        this.id = id;
        this.chat_id = chat_id;
        this.fio = fio;
    }

    public UserDto(Long id, Long chat_id) {
        this.id = id;
        this.chat_id = chat_id;
    }

}
