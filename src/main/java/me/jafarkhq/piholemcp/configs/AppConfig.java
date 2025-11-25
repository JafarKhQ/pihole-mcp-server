package me.jafarkhq.piholemcp.configs;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;


@Configuration
@EnableFeignClients(basePackages = "me.jafarkhq.piholemcp.pihole.clients")
public class AppConfig {

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Bean
    public JacksonJsonHttpMessageConverter jacksonJsonHttpMessageConverter(JsonMapper mapper) {
        return new JacksonJsonHttpMessageConverter(mapper);
    }

}
