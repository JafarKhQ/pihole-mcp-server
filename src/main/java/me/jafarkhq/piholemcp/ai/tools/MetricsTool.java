package me.jafarkhq.piholemcp.ai.tools;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.services.FtlInfoService;
import me.jafarkhq.piholemcp.pihole.services.MetricsService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


@Component
@RequiredArgsConstructor
public class MetricsTool {

    ObjectMapper objectMapper;

    MetricsService metricsService;
    FtlInfoService ftlInfoService;

    @Tool(name = "pihole_status_summary",
            description = "Get Pi-hole status summary including total queries, blocked queries, and more.")
    public String getStatusSummary() {
        return toJson(metricsService.getStatusSummary());
    }

    @Tool(name = "pihole_system_info",
            description = "Get detailed system information of the Pi-hole server.")
    public String getSystemInfo() {
        return toJson(ftlInfoService.getSystemInfo());
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

}
