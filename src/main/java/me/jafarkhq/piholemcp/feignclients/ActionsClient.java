package me.jafarkhq.piholemcp.feignclients;

import org.springframework.web.bind.annotation.PostMapping;


@PiholeClient(name = "piholeActions", path = "action")
public interface ActionsClient {

    @PostMapping
    String restartDns();

}
