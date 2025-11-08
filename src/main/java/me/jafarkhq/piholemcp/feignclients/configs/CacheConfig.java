package me.jafarkhq.piholemcp.feignclients.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CACHE_NAME_AUTH = "authToken";

    @Bean
    public CacheManager cacheManager(@Value("${PIHOLE_TOKEN_VALIDITY_SECONDS}") int tokenExpirySeconds) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CACHE_NAME_AUTH);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(tokenExpirySeconds - 2, TimeUnit.SECONDS)
                .maximumSize(1));
        return cacheManager;
    }
}
