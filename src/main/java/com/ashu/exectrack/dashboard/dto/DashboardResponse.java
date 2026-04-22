package com.ashu.exectrack.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponse {

    private Long taskId;
    private String taskName;

    private int totalDays;
    private int completedDays;
    private int missedDays;

    private double completionPercentage;

    private int currentStreak;

    private List<DailyStatus> last7Days;
}