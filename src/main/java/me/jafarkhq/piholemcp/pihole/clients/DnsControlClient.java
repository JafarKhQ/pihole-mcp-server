package me.jafarkhq.piholemcp.pihole.clients;

import me.jafarkhq.piholemcp.pihole.models.requests.BlockingStatusRequest;
import me.jafarkhq.piholemcp.pihole.models.responses.BlockingStatusResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@PiholeClient(name = "piholeDnsControl", path = "dns/blocking")
public interface DnsControlClient {

    @GetMapping
    BlockingStatusResponse getCurrentStatus();

    @PostMapping
    BlockingStatusResponse setCurrentStatus(@RequestBody BlockingStatusRequest request);

}
