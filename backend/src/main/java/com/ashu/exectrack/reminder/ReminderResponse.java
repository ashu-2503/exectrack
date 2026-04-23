/*
A task triggers a reminder only if ALL are true:
    1. reminderEnabled = true
    2. task is ACTIVE today (frequency)
    3. now ∈ [startTime, endTime]
    4. task NOT DONE today
    5. window not expired → otherwise mark MISSED and stop
*/

package com.ashu.exectrack.reminder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReminderResponse {

    private Long taskId;
    private String taskName;
    private Integer reminderInterval;
}