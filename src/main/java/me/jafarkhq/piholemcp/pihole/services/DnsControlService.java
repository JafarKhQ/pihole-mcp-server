package me.jafarkhq.piholemcp.pihole.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.clients.DnsControlClient;
import me.jafarkhq.piholemcp.pihole.models.requests.BlockingStatusRequest;
import me.jafarkhq.piholemcp.pihole.models.responses.BlockingStatusResponse;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DnsControlService {

    DnsControlClient client;

    public BlockingStatusResponse getCurrentStatus() {
        return client.getCurrentStatus();
    }

    public BlockingStatusResponse setCurrentStatus(boolean blocking, Integer timer) {
        return client.setCurrentStatus(new BlockingStatusRequest(blocking, blocking ? null : timer));
    }

}
