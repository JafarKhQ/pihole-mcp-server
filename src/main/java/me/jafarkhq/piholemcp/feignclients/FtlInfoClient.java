package me.jafarkhq.piholemcp.feignclients;

import org.springframework.web.bind.annotation.GetMapping;


@PiholeClient(name = "piholeFtlInfo")
public interface FtlInfoClient {

    @GetMapping("info/system")
    String systemInfo();

}
