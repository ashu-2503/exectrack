package com.ashu.exectrack.dashboard;

import com.ashu.exectrack.common.TaskStatus;
import com.ashu.exectrack.dashboard.dto.DayStatus;
import com.ashu.exectrack.dashboard.dto.DashboardResponse;
import com.ashu.exectrack.task.Task;
import com.ashu.exectrack.task.TaskRepository;
import com.ashu.exectrack.tasklog.TaskLog;
import com.ashu.exectrack.tasklog.TaskLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;
    private final TaskLogRepository taskLogRepository;

    public List<DashboardResponse> getMonthlyDashboard(int year, int month) {

        List<Task> tasks = taskRepository.findAll();
        List<DashboardResponse> responseList = new ArrayList<>();

        YearMonth yearMonth = YearMonth.of(year, month);
        int totalDaysInMonth = yearMonth.lengthOfMonth();

        LocalDate today = LocalDate.now();

        for (Task task : tasks) {

            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();

            List<TaskLog> logs = taskLogRepository
                    .findByTaskIdAndDateBetween(task.getId(), startDate, endDate);

            Map<LocalDate, TaskLog> logMap = new HashMap<>();
            for (TaskLog log : logs) {
                logMap.put(log.getDate(), log);
            }

            List<DayStatus> dayStatuses = new ArrayList<>();

            int completed = 0;

            for (int day = 1; day <= totalDaysInMonth; day++) {

                LocalDate currentDate = yearMonth.atDay(day);

                TaskStatus status;

                // 🔥 FIX 1: before task creation
                if (currentDate.isBefore(task.getCreatedAt())) {
                    status = TaskStatus.FUTURE;
                }

                // log exists
                else if (logMap.containsKey(currentDate)) {

                    status = TaskStatus.valueOf(String.valueOf(logMap.get(currentDate).getStatus()));

                    if (status == TaskStatus.DONE) {
                        completed++;
                    }

                }

                // no log
                else {

                    if (currentDate.isAfter(today)) {
                        status = TaskStatus.FUTURE;
                    } else if (currentDate.equals(today)) {
                        status = TaskStatus.PENDING;
                    } else {
                        status = TaskStatus.MISSED;
                    }
                }

                dayStatuses.add(
                        DayStatus.builder()
                                .day(day)
                                .status(status)
                                .build()
                );
            }

            // 🔥 FIX 2: correct effective days (from task creation)
            LocalDate effectiveStart = task.getCreatedAt().isAfter(startDate)
                    ? task.getCreatedAt()
                    : startDate;

            int effectiveDays;

            if (year == today.getYear() && month == today.getMonthValue()) {
                effectiveDays = (int) ChronoUnit.DAYS.between(effectiveStart, today) + 1;
            } else {
                effectiveDays = (int) ChronoUnit.DAYS.between(effectiveStart, endDate) + 1;
            }

            // avoid division by zero
            effectiveDays = Math.max(effectiveDays, 1);

            double percentage = (completed / (double) effectiveDays) * 100;

            int longestStreak = calculateLongestStreak(logMap, yearMonth, totalDaysInMonth, task);

            responseList.add(
                    DashboardResponse.builder()
                            .taskId(task.getId())
                            .taskName(task.getName())
                            .dailyStatus(dayStatuses)
                            .completedDays(completed)
                            .totalDays(totalDaysInMonth)
                            .completionPercentage(percentage)
                            .longestStreak(longestStreak)
                            .build()
            );
        }

        return responseList;
    }

    private int calculateLongestStreak(Map<LocalDate, TaskLog> logMap,
                                       YearMonth yearMonth,
                                       int totalDays,
                                       Task task) {

        int maxStreak = 0;
        int currentStreak = 0;

        for (int day = 1; day <= totalDays; day++) {

            LocalDate date = yearMonth.atDay(day);

            // ignore days before task creation
            if (date.isBefore(task.getCreatedAt())) {
                continue;
            }

            if (logMap.containsKey(date)
                    && TaskStatus.valueOf(String.valueOf(logMap.get(date).getStatus())) == TaskStatus.DONE) {

                currentStreak++;
                maxStreak = Math.max(maxStreak, currentStreak);

            } else {
                currentStreak = 0;
            }
        }

        return maxStreak;
    }
}