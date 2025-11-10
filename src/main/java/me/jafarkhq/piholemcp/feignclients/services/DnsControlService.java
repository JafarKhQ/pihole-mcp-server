package me.jafarkhq.piholemcp.feignclients.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.feignclients.DnsControlClient;
import me.jafarkhq.piholemcp.feignclients.models.BlockingStatusRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DnsControlService {

    DnsControlClient client;

    public String getCurrentStatus() {
        return client.getCurrentStatus();
    }

    public String setCurrentStatus(boolean blocking, Integer timer) {
        return client.setCurrentStatus(new BlockingStatusRequest(blocking, blocking ? null : timer));
    }

}
