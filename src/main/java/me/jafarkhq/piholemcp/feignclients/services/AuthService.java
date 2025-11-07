package me.jafarkhq.piholemcp.feignclients.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import me.jafarkhq.piholemcp.feignclients.AuthClient;
import me.jafarkhq.piholemcp.feignclients.models.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    @NonFinal
    byte[] token;
    @Value("${PIHOLE_APP_PASSWORD}")
    byte[] apiKey;
    AuthClient client;

    public byte[] login() {
        return token;
    }

    public void refreshToken() {
        try {
            // TODO: validate the response
            token = client.login(new AuthRequest(new String(apiKey))).session().sid();
        } catch (Exception e) {
            // TODO: handle errors
        }
    }

}
