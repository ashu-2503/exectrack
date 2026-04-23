package com.ashu.exectrack.reminder;

import com.ashu.exectrack.reminder.ReminderResponse;
import com.ashu.exectrack.reminder.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @GetMapping
    public List<ReminderResponse> getReminders() {
        return reminderService.getActiveReminders();
    }
}