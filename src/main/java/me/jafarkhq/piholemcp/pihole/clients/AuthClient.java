package me.jafarkhq.piholemcp.pihole.clients;

import me.jafarkhq.piholemcp.pihole.models.requests.AuthRequest;
import me.jafarkhq.piholemcp.pihole.models.responses.AuthResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@PiholeClient(name = "piholeAuth", path = "auth", configuration = {})
public interface AuthClient {

    @PostMapping
    AuthResponse login(@RequestBody AuthRequest request);

}
