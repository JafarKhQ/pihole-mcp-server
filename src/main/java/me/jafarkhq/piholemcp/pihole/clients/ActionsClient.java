package me.jafarkhq.piholemcp.pihole.clients;

import me.jafarkhq.piholemcp.pihole.models.responses.RestartDnsResponse;
import org.springframework.web.bind.annotation.PostMapping;


@PiholeClient(name = "piholeActions", path = "action")
public interface ActionsClient {

    @PostMapping("restartdns")
    RestartDnsResponse restartDns();

}
