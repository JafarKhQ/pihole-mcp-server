package me.jafarkhq.piholemcp.feignclients.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.feignclients.ActionsClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ActionsService {

    ActionsClient client;

    public String restartDns() {
        return client.restartDns();
    }

}
