package me.jafarkhq.piholemcp.pihole.services;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.configs.CacheConfig;
import me.jafarkhq.piholemcp.pihole.clients.AuthClient;
import me.jafarkhq.piholemcp.pihole.models.requests.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@ConditionalOnExpression("!'${pihole.app_password:}'.isEmpty()")
public class AuthService {

    private static final String KEY_AUTH_SID = "auth-sid-key";

    @Value("${pihole.app_password}")
    byte[] apiKey;
    @Lazy
    AuthClient client;
    Cache<String, CacheConfig.CachedWithExpiry> tokenCache;

    public String getValidToken() {
        return tokenCache.get(KEY_AUTH_SID, key -> login()).value();
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
            throw new RuntimeException("Failed to generate the token, make sure PIHOLE_APP_PASSWORD is correct.", e);
        }
    }

}
