package com.ashu.exectrack.task;

import com.ashu.exectrack.common.FrequencyType;
import com.ashu.exectrack.common.TaskUnit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Basic Info
    private String name;
    @Enumerated(EnumType.STRING)
    private TaskUnit unit; // COUNT, MINUTES, BOOLEAN
    private Integer targetValue;
    private LocalDate createdAt;

    // Reminder Config
    private Boolean reminderEnabled;
    private Integer reminderInterval; // in minutes
    private LocalTime startTime; // reminder window start
    private LocalTime endTime;   // reminder window end

    // Frequency Config
    @Enumerated(EnumType.STRING)
    private FrequencyType frequencyType; // DAILY, WEEKLY, CUSTOM
    private String activeDays; // e.g. "MONDAY,TUESDAY"
    private LocalDate specificDate; // for CUSTOM tasks

    private Boolean active; // for soft disable
}
