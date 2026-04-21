package com.ashu.exectrack.tasklog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {
    List<TaskLog> findByTaskId(Long taskId);
    List<TaskLog> findByTaskIdAndDateBetween(Long taskId, LocalDate start, LocalDate end);
    List<TaskLog> findByDate(LocalDate date);
    Optional<TaskLog> findByTaskIdAndDate(Long taskId, LocalDate date);
}