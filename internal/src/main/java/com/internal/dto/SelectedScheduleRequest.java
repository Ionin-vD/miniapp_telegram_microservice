package com.internal.dto;

import lombok.Data;

@Data
public class SelectedScheduleRequest {
    private Long scheduleId;
    private Long userId;
    private Long themeId;
}
