package com.ashu.exectrack.tasklog;

import com.ashu.exectrack.common.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "task_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskId;
    private LocalDate date;
    private Integer valueDone;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

}