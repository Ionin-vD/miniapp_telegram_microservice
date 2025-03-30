package com.api.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private Long chat_id;
    private String fio;
    private boolean isAdmin;
    private boolean isAuth;
    private boolean isDeleted;
}
