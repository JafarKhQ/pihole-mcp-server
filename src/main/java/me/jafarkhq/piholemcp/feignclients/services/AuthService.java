package me.jafarkhq.piholemcp.feignclients.services;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.configs.CacheConfig;
import me.jafarkhq.piholemcp.feignclients.AuthClient;
import me.jafarkhq.piholemcp.feignclients.models.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String KEY_AUTH_SID = "auth-sid-key";

    @Value("${pihole.app_password}")
    byte[] apiKey;
    AuthClient client;
    Cache<String, CacheConfig.CachedWithExpiry> tokenCache;

    public String getValidToken() {
        return tokenCache.get(KEY_AUTH_SID, key -> {
            try {
                // TODO: validate the response
                var session = client.login(new AuthRequest(new String(apiKey))).session();
                return new CacheConfig.CachedWithExpiry(session.sid(), session.validity());
            } catch (Exception e) {
                // TODO: handle errors
                throw new RuntimeException("Failed to generate the token", e);
            }
        }).value();
    }

    public void invalidateToken() {
        tokenCache.invalidate(KEY_AUTH_SID);
    }

}
