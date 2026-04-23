package com.ashu.exectrack.tasklog;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class TaskLogController {

    private final TaskLogService taskLogService;

    @PostMapping("/{taskId}")
    public TaskLog logTask(
            @PathVariable Long taskId,
            @RequestParam Integer valueDone
    ) {
        return taskLogService.logTask(taskId, valueDone);
    }
}