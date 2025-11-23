package me.jafarkhq.piholemcp.pihole.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.clients.ActionsClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ActionsService {

    ActionsClient client;

    public String restartDns() {
        return client.restartDns();
    }

}
