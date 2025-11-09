package me.jafarkhq.piholemcp.feignclients;

import me.jafarkhq.piholemcp.feignclients.configs.AuthClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@FeignClient(url = "${pihole.url}")
public @interface PiholeClient {

    @AliasFor(attribute = "name", annotation = FeignClient.class)
    String name() default "";

    @AliasFor(attribute = "path", annotation = FeignClient.class)
    String path() default "";

    @AliasFor(attribute = "configuration", annotation = FeignClient.class)
    Class<?>[] configuration() default AuthClientConfig.class;
}
