package com.ashu.exectrack.dashboard;


import com.ashu.exectrack.dashboard.dto.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public List<DashboardResponse> getDashboard() {
        return dashboardService.getDashboard();
    }
}