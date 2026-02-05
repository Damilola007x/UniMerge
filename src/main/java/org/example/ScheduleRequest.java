package com.unimerge.dto;

import lombok.Data;

@Data
public class ScheduleRequest {
    private String studentId;
    private String courseCode;
    private String preferredDay; // e.g., "Monday"
}