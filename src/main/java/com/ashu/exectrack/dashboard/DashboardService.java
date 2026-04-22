package com.ashu.exectrack.dashboard;

import com.ashu.exectrack.dashboard.dto.DailyStatus;
import com.ashu.exectrack.dashboard.dto.DashboardResponse;
import com.ashu.exectrack.task.Task;
import com.ashu.exectrack.task.TaskRepository;
import com.ashu.exectrack.tasklog.TaskLog;
import com.ashu.exectrack.tasklog.TaskLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;
    private final TaskLogRepository taskLogRepository;

    public List<DashboardResponse> getDashboard() {

        List<Task> tasks = taskRepository.findAll();
        List<DashboardResponse> responseList = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6);

        for (Task task : tasks) {

            List<TaskLog> logs = taskLogRepository
                    .findByTaskIdAndDateBetween(task.getId(), startDate, today);

            Map<LocalDate, TaskLog> logMap = new HashMap<>();
            for (TaskLog log : logs) {
                logMap.put(log.getDate(), log);
            }

            List<DailyStatus> last7Days = new ArrayList<>();

            int completed = 0;
            int missed = 0;

            for (int i = 0; i < 7; i++) {
                LocalDate date = startDate.plusDays(i);

                if (logMap.containsKey(date)) {
                    String status = logMap.get(date).getStatus();

                    if ("DONE".equals(status)) completed++;
                    if ("MISSED".equals(status)) missed++;

                    last7Days.add(
                            DailyStatus.builder()
                                    .date(date)
                                    .status(status)
                                    .build()
                    );
                } else {
                    missed++;

                    last7Days.add(
                            DailyStatus.builder()
                                    .date(date)
                                    .status("MISSED")
                                    .build()
                    );
                }
            }

            double percentage = (completed / 7.0) * 100;

            int streak = calculateStreak(logMap, today);

            responseList.add(
                    DashboardResponse.builder()
                            .taskId(task.getId())
                            .taskName(task.getName())
                            .totalDays(7)
                            .completedDays(completed)
                            .missedDays(missed)
                            .completionPercentage(percentage)
                            .currentStreak(streak)
                            .last7Days(last7Days)
                            .build()
            );
        }

        return responseList;
    }

    private int calculateStreak(Map<LocalDate, TaskLog> logMap, LocalDate today) {

        int streak = 0;

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);

            if (logMap.containsKey(date)
                    && "DONE".equals(logMap.get(date).getStatus())) {
                streak++;
            } else {
                break;
            }
        }

        return streak;
    }
}