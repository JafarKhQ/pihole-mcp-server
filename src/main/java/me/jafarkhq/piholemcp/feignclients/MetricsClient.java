package me.jafarkhq.piholemcp.feignclients;

import me.jafarkhq.piholemcp.feignclients.configs.AuthClientConfig;
import me.jafarkhq.piholemcp.feignclients.models.StatusSummeryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(url = "${PIHOLE_URL}", configuration = AuthClientConfig.class)
public interface MetricsClient {

    @GetMapping("stats/summary")
    StatusSummeryResponse getStatusSummary();

}
