package me.jafarkhq.piholemcp.ai.tools;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.feignclients.models.StatusSummeryResponse;
import me.jafarkhq.piholemcp.feignclients.services.FtlInfoService;
import me.jafarkhq.piholemcp.feignclients.services.MetricsService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MetricsTool {

    MetricsService metricsService;
    FtlInfoService ftlInfoService;

    @Tool(name = "Pi-hole Status Summary",
            description = "Get Pi-hole status summary including total queries, blocked queries, and more.")
    public StatusSummeryResponse getStatusSummary() {
        return metricsService.getStatusSummary();
    }

    @Tool(name = "Pi-hole System Info",
            description = "Get detailed system information of the Pi-hole server.")
    public String getSystemInfo() {
        return ftlInfoService.getSystemInfo();
    }

}
