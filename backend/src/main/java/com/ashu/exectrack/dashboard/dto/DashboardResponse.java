package com.ashu.exectrack.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponse {

    private Long taskId;
    private String taskName;

    private List<DayStatus> dailyStatus;

    private int completedDays;
    private int totalDays;
    private double completionPercentage;

    private int longestStreak;
}