package me.jafarkhq.piholemcp.ai.tools;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.models.responses.StatusSummeryResponse;
import me.jafarkhq.piholemcp.pihole.services.FtlInfoService;
import me.jafarkhq.piholemcp.pihole.services.MetricsService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MetricsTool {

    MetricsService metricsService;
    FtlInfoService ftlInfoService;

    @Tool(name = "pihole_status_summary",
            description = "Get Pi-hole status summary including total queries, blocked queries, and more.")
    public StatusSummeryResponse getStatusSummary() {
        return metricsService.getStatusSummary();
    }

    @Tool(name = "pihole_system_info",
            description = "Get detailed system information of the Pi-hole server.")
    public String getSystemInfo() {
        return ftlInfoService.getSystemInfo();
    }

}
