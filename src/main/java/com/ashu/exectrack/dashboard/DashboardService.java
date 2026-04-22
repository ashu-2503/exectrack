package com.ashu.exectrack.dashboard;

import com.ashu.exectrack.dashboard.dto.DashboardResponse;
import com.ashu.exectrack.dashboard.dto.DayStatus;
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

                String status;

                if (logMap.containsKey(currentDate)) {
                    status = logMap.get(currentDate).getStatus();

                    if ("DONE".equals(status)) {
                        completed++;
                    }

                } else {

                    if (currentDate.isAfter(today)) {
                        status = "FUTURE";
                    } else if (currentDate.equals(today)) {
                        status = "PENDING";
                    } else {
                        status = "MISSED";
                    }
                }

                dayStatuses.add(
                        DayStatus.builder()
                                .day(day)
                                .status(status)
                                .build()
                );
            }

            int daysPassed = (today.getMonthValue() == month && today.getYear() == year)
                    ? today.getDayOfMonth()
                    : totalDaysInMonth;

            double percentage = (completed / (double) daysPassed) * 100;

            int longestStreak = calculateLongestStreak(logMap, yearMonth, totalDaysInMonth);

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
                                       int totalDays) {

        int maxStreak = 0;
        int currentStreak = 0;

        for (int day = 1; day <= totalDays; day++) {

            LocalDate date = yearMonth.atDay(day);

            if (logMap.containsKey(date)
                    && "DONE".equals(logMap.get(date).getStatus())) {

                currentStreak++;
                maxStreak = Math.max(maxStreak, currentStreak);

            } else {
                currentStreak = 0;
            }
        }

        return maxStreak;
    }
}