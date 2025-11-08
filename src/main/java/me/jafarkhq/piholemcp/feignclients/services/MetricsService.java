package me.jafarkhq.piholemcp.feignclients.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.feignclients.MetricsClient;
import me.jafarkhq.piholemcp.feignclients.models.StatusSummeryResponse;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MetricsService {

    MetricsClient client;

    public StatusSummeryResponse getStatusSummary() {
        return client.getStatusSummary();
    }

}
