package me.jafarkhq.piholemcp.pihole.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.clients.ActionsClient;
import me.jafarkhq.piholemcp.pihole.models.responses.RestartDnsResponse;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ActionsService {

    ActionsClient client;

    public RestartDnsResponse restartDns() {
        return client.restartDns();
    }

}
