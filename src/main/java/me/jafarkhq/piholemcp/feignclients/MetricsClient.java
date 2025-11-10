package me.jafarkhq.piholemcp.feignclients;

import me.jafarkhq.piholemcp.feignclients.models.StatusSummeryResponse;
import org.springframework.web.bind.annotation.GetMapping;


@PiholeClient(name = "piholeMetrics")
public interface MetricsClient {

    @GetMapping("stats/summary")
    StatusSummeryResponse getStatusSummary();

}
