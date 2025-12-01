package me.jafarkhq.piholemcp.pihole.services;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.configs.CacheConfig;
import me.jafarkhq.piholemcp.pihole.clients.AuthClient;
import me.jafarkhq.piholemcp.pihole.models.requests.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String KEY_AUTH_SID = "auth-sid-key";

    @Value("${pihole.app_password:}")
    byte[] apiKey;
    AuthClient client;
    Cache<String, CacheConfig.CachedWithExpiry> tokenCache;

    public boolean isAuthEnabled() {
        return apiKey != null && apiKey.length > 0;
    }

    public Optional<String> getValidToken() {
        if (!isAuthEnabled()) {
            return Optional.empty();
        }

        return Optional.of(tokenCache.get(KEY_AUTH_SID, key -> login()).value());
    }

    public void invalidateToken() {
        tokenCache.invalidate(KEY_AUTH_SID);
    }

    private CacheConfig.CachedWithExpiry login() {
        try {
            // TODO: validate the response
            var session = client.login(new AuthRequest(new String(apiKey))).session();
            return new CacheConfig.CachedWithExpiry(session.sid(), session.validity());
        } catch (Exception e) {
            // TODO: handle errors
            throw new RuntimeException("Failed to generate the token", e);
        }
    }

}
