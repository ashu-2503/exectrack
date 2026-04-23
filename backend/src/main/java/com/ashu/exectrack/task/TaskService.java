package com.ashu.exectrack.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {

        // set system-controlled fields
        task.setCreatedAt(LocalDate.now());

        // default values (important for stability)
        if (task.getReminderEnabled() == null) {
            task.setReminderEnabled(false);
        }

        if (task.getReminderInterval() == null) {
            task.setReminderInterval(60); // default 60 mins
        }

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
