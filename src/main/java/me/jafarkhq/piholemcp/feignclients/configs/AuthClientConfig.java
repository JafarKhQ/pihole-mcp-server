package me.jafarkhq.piholemcp.feignclients.configs;

import feign.RequestInterceptor;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import me.jafarkhq.piholemcp.feignclients.services.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import static java.util.concurrent.TimeUnit.SECONDS;


public class AuthClientConfig {

    private static final String HEADER_NAME_SID = "X-FTL-SID";

    @Bean
    public RequestInterceptor apiKeyRequestInterceptor(AuthService authService) {
        return requestTemplate -> {
            // just pass the APIKey as header
            requestTemplate.header(HEADER_NAME_SID, new String(authService.getValidToken()));
        };
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 2); // maxAttempts = 2 (1 original + 1 retry)
    }

    @Bean
    public ErrorDecoder errorDecoder(AuthService authService) {
        return (methodKey, response) -> {
            if (response.status() == HttpStatus.UNAUTHORIZED.value()) {
                authService.getValidToken(); // Force renewal
                return new RetryableException(response.status(), "Token expired, retrying...",
                        response.request().httpMethod(), (Long) null, response.request());
            }

            return new ErrorDecoder.Default().decode(methodKey, response);
        };
    }

}
