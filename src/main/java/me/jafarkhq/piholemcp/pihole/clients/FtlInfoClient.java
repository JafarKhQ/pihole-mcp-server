package me.jafarkhq.piholemcp.pihole.clients;

import me.jafarkhq.piholemcp.pihole.models.responses.SystemInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;


@PiholeClient(name = "piholeFtlInfo")
public interface FtlInfoClient {

    @GetMapping("info/system")
    SystemInfoResponse systemInfo();

}
