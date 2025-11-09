package me.jafarkhq.piholemcp.ai.tools;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.feignclients.services.DnsControlService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionsTool {

    DnsControlService dnsControlService;

    @Tool(name = "Restart Pi-hole FTL Service",
            description = "Restart the Pi-hole FTL (Faster Than Light) DNS service to apply changes or resolve issues.")
    public String restartFtlService() {
        // Placeholder for actual restart logic
        return "Pi-hole FTL service has been restarted.";
    }

    @Tool(name = "Get Pi-hole DNS Blocking Status",
            description = "Retrieve the current DNS blocking status of the Pi-hole server and the remaining seconds until blocking mode is automatically changed.")
    public String getBlockingStatus() {
        return dnsControlService.getCurrentStatus();
    }

    @Tool(name = "Set Pi-hole DNS Blocking Status",
            description = "Enable or disable DNS blocking on the Pi-hole server. Optionally set a timer for how long to disable blocking.")
    public String setBlockingStatus(@ToolParam(description = "boolean true to enable DNS blocking or false to disable DNS blocking") boolean blocking,
                                    @ToolParam(description = "in case of disabling DNS blocking provide the period in seconds, otherwise send null") Integer seconds) {
        return dnsControlService.setCurrentStatus(blocking, seconds);
    }

}
