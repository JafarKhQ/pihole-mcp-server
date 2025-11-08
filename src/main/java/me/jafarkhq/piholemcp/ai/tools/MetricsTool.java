package me.jafarkhq.piholemcp.ai.tools;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.feignclients.models.StatusSummeryResponse;
import me.jafarkhq.piholemcp.feignclients.services.MetricsService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MetricsTool {

    MetricsService service;

    @Tool(description = "Get Pi-hole status summary including total queries, blocked queries, and more.")
    public StatusSummeryResponse getStatusSummary() {
        return service.getStatusSummary();
    }

}
