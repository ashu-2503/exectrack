package com.ashu.exectrack.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DailyStatus {

    private LocalDate date;
    private String status; // DONE / PARTIAL / MISSED
}