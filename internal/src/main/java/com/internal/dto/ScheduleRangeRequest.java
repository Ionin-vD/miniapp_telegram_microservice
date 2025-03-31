package com.internal.dto;

import lombok.Data;

@Data
public class ScheduleRangeRequest {
    private Long course_id;
    private String date;
    private String time;
}
