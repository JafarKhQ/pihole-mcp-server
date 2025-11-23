package me.jafarkhq.piholemcp.pihole.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.clients.MetricsClient;
import me.jafarkhq.piholemcp.pihole.models.responses.StatusSummeryResponse;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MetricsService {

    MetricsClient client;

    public StatusSummeryResponse getStatusSummary() {
        return client.getStatusSummary();
    }

}
