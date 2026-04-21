package com.ashu.exectrack.tasklog;

import com.ashu.exectrack.task.Task;
import com.ashu.exectrack.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskLogService {

    private final TaskLogRepository taskLogRepository;
    private final TaskRepository taskRepository;

    public TaskLog logTask(Long taskId, Integer valueDone) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        LocalDate today = LocalDate.now();

        String status;
        if (valueDone >= task.getTargetValue()) {
            status = "DONE";
        } else if (valueDone > 0) {
            status = "PARTIAL";
        } else {
            status = "MISSED";
        }
        // check if log already exists
        Optional<TaskLog> existingLog = taskLogRepository.findByTaskIdAndDate(taskId, today);
        if (existingLog.isPresent()) {
            TaskLog log = existingLog.get();
            log.setValueDone(valueDone);
            log.setStatus(status);
            return taskLogRepository.save(log);
        }
        TaskLog log = TaskLog.builder()
                .taskId(taskId)
                .date(today)
                .valueDone(valueDone)
                .status(status)
                .build();
        return taskLogRepository.save(log);
    }}