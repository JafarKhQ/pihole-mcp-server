package me.jafarkhq.piholemcp.pihole.configs;

import feign.RequestInterceptor;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import me.jafarkhq.piholemcp.pihole.services.AuthService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import static java.util.concurrent.TimeUnit.SECONDS;


public class AuthClientConfig {

    private static final String HEADER_NAME_SID = "X-FTL-SID";

    @Bean
    @ConditionalOnBean(AuthService.class)
    public RequestInterceptor apiKeyRequestInterceptor(AuthService authService) {
        return requestTemplate -> requestTemplate.header(HEADER_NAME_SID, authService.getValidToken());
    }

    @Bean
    @ConditionalOnBean(AuthService.class)
    public Retryer retryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 2);
    }

    @Bean
    @ConditionalOnBean(AuthService.class)
    public ErrorDecoder errorDecoder(AuthService authService) {
        return (methodKey, response) -> {
            if (response.status() == HttpStatus.UNAUTHORIZED.value()) {
                authService.invalidateToken(); // Force renewal
                return new RetryableException(response.status(), "Token expired, retrying...",
                        response.request().httpMethod(), (Long) null, response.request());
            }

            return new ErrorDecoder.Default().decode(methodKey, response);
        };
    }

}
