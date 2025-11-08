package me.jafarkhq.piholemcp.configs;

import me.jafarkhq.piholemcp.ai.tools.MetricsTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AiConfig {

    @Bean
    public ToolCallbackProvider weatherTools(MetricsTool metricsTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(metricsTool)
                .build();
    }

}
