package com.ashu.exectrack.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DayStatus {

    private int day;
    private String status; // DONE, PARTIAL, MISSED, PENDING
}