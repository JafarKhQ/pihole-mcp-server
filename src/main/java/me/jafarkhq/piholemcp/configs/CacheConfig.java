package me.jafarkhq.piholemcp.configs;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Cache<String, CachedWithExpiry> tokenCache() {
        return Caffeine.newBuilder()
                .expireAfter(new Expiry<String, CachedWithExpiry>() {
                    @Override
                    public long expireAfterCreate(String key, CachedWithExpiry value, long currentTime) {
                        return calculateExpiry(value.expiresIn());
                    }

                    @Override
                    public long expireAfterUpdate(String key, CachedWithExpiry value, long currentTime, long currentDuration) {
                        return calculateExpiry(value.expiresIn());
                    }

                    @Override
                    public long expireAfterRead(String key, CachedWithExpiry value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                })
                .maximumSize(1)
                .build();
    }

    private long calculateExpiry(int expiresIn) {
        return TimeUnit.SECONDS.toNanos(expiresIn - 2);
    }

    public record CachedWithExpiry(String value, int expiresIn) {
    }

}
