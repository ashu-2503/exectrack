package com.ashu.exectrack.reminder;


import com.ashu.exectrack.common.TaskStatus;
import com.ashu.exectrack.task.Task;

import com.ashu.exectrack.task.TaskRepository;
import com.ashu.exectrack.tasklog.TaskLog;
import com.ashu.exectrack.tasklog.TaskLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final TaskRepository taskRepository;
    private final TaskLogRepository taskLogRepository;

    public List<ReminderResponse> getActiveReminders() {

        List<Task> tasks = taskRepository.findAll();
        List<ReminderResponse> reminders = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        DayOfWeek todayDay = today.getDayOfWeek();

        for (Task task : tasks) {
            // 1. skip if reminder disabled
            if (Boolean.FALSE.equals(task.getReminderEnabled())) continue;
            // 2. skip if task not active today
            if (!isTaskActiveToday(task, today, todayDay)) continue;
            Optional<TaskLog> logOpt =
                    taskLogRepository.findByTaskIdAndDate(task.getId(), today);
            // 3. skip if already DONE
            if (logOpt.isPresent() && logOpt.get().getStatus() == TaskStatus.DONE) {
                continue;
            }
            // 4. handle time window
            if (!isWithinWindow(task, now)) {
                // if window expired → mark MISSED and stop for today
                if (isWindowExpired(task, now)) {
                    markMissedIfRequired(task, logOpt, today);
                }
                continue;
            }
            // 5. eligible for reminder
            reminders.add(
                    ReminderResponse.builder()
                            .taskId(task.getId())
                            .taskName(task.getName())
                            .reminderInterval(task.getReminderInterval())
                            .build()
            );
        }
        return reminders;
    }

    // ------------------- HELPERS -------------------

    private boolean isTaskActiveToday(Task task, LocalDate today, DayOfWeek todayDay) {
        if (task.getFrequencyType() == null) return true;

        switch (task.getFrequencyType()) {
            case DAILY:
                return true;
            case WEEKLY:
                if (task.getActiveDays() == null || task.getActiveDays().isEmpty()) {
                    return false;
                }
                // activeDays format: "MONDAY,WEDNESDAY"
                return Arrays.stream(task.getActiveDays().split(","))
                        .anyMatch(day -> day.trim().equalsIgnoreCase(todayDay.name()));
            case CUSTOM:
                return today.equals(task.getSpecificDate());
            default:
                return false;
        }
    }

    private boolean isWithinWindow(Task task, LocalTime now) {
        if (task.getStartTime() == null || task.getEndTime() == null) {
            return true; // no restriction
        }
        return !now.isBefore(task.getStartTime())
                && !now.isAfter(task.getEndTime());
    }

    private boolean isWindowExpired(Task task, LocalTime now) {
        if (task.getEndTime() == null) return false;
        return now.isAfter(task.getEndTime());
    }

    private void markMissedIfRequired(Task task,
                                      Optional<TaskLog> logOpt,
                                      LocalDate today) {
        if (logOpt.isPresent()) {
            TaskLog log = logOpt.get();
            // do not overwrite DONE
            if (log.getStatus() != TaskStatus.DONE) {
                log.setStatus(TaskStatus.MISSED);
                log.setValueDone(0);
                taskLogRepository.save(log);
            }
        } else {
            TaskLog log = TaskLog.builder()
                    .taskId(task.getId())
                    .date(today)
                    .valueDone(0)
                    .status(TaskStatus.MISSED)
                    .build();
            taskLogRepository.save(log);
        }
    }
}