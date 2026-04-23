package com.ashu.exectrack.dashboard.dto;

import com.ashu.exectrack.common.TaskStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DayStatus {

    private int day;
    private TaskStatus status; // DONE, PARTIAL, MISSED, PENDING
}