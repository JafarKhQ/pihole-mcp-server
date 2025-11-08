package me.jafarkhq.piholemcp.feignclients;

import me.jafarkhq.piholemcp.feignclients.models.AuthRequest;
import me.jafarkhq.piholemcp.feignclients.models.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "piholeAuth", url = "${pihole.url}", path = "auth")
public interface AuthClient {

    @PostMapping
    AuthResponse login(@RequestBody AuthRequest request);

}
