package me.jafarkhq.piholemcp.configs;

import me.jafarkhq.piholemcp.ai.tools.ActionsTool;
import me.jafarkhq.piholemcp.ai.tools.MetricsTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AiConfig {

    @Bean
    public ToolCallbackProvider piholeTools(MetricsTool metricsTool, ActionsTool actionsTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(metricsTool)
                .toolObjects(actionsTool)
                .build();
    }

}
