package me.jafarkhq.piholemcp.pihole.clients;

import me.jafarkhq.piholemcp.pihole.models.responses.StatusSummeryResponse;
import org.springframework.web.bind.annotation.GetMapping;


@PiholeClient(name = "piholeMetrics")
public interface MetricsClient {

    @GetMapping("stats/summary")
    StatusSummeryResponse getStatusSummary();

}
