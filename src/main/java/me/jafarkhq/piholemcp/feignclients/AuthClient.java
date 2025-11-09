package me.jafarkhq.piholemcp.feignclients;

import me.jafarkhq.piholemcp.feignclients.models.AuthRequest;
import me.jafarkhq.piholemcp.feignclients.models.AuthResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@PiholeClient(name = "piholeAuth", path = "auth", configuration = {})
public interface AuthClient {

    @PostMapping
    AuthResponse login(@RequestBody AuthRequest request);

}
