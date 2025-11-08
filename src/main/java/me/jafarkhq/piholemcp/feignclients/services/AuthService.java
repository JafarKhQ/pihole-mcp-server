package me.jafarkhq.piholemcp.feignclients.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import me.jafarkhq.piholemcp.feignclients.AuthClient;
import me.jafarkhq.piholemcp.feignclients.configs.CacheConfig;
import me.jafarkhq.piholemcp.feignclients.models.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${PIHOLE_APP_PASSWORD}")
    byte[] apiKey;
    AuthClient client;

    @Cacheable(value = CacheConfig.CACHE_NAME_AUTH, key = "api_sid_token")
    public byte[] getValidToken() {
        try {
            // TODO: validate the response
            return client.login(new AuthRequest(new String(apiKey))).session().sid();
        } catch (Exception e) {
            // TODO: handle errors
            throw new RuntimeException("Failed to generate the token", e);
        }
    }

}
