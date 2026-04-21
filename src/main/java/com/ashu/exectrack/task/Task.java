package com.ashu.exectrack.task;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

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
    private String name;
    private String type;
    private Integer targetValue;
    private String unit;
    private LocalDate createdAt;
    private Boolean reminderEnabled;
    private Integer reminderInterval; // in minutes
}
