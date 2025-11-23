package me.jafarkhq.piholemcp.ai.tools;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.services.ActionsService;
import me.jafarkhq.piholemcp.pihole.services.DnsControlService;
import me.jafarkhq.piholemcp.pihole.services.DomainManageService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ActionsTool {

    ActionsService actionsService;
    DnsControlService dnsControlService;
    DomainManageService domainManageService;

    @Tool(name = "Restart Pi-hole FTL Service",
            description = "Restart the Pi-hole FTL (Faster Than Light) DNS service to apply changes or resolve issues.")
    public String restartFtlService() {
        return actionsService.restartDns();
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

    @Tool(name = "Add New Domain to Pi-hole Blacklist/Whitelist",
            description = """
                    Add a new domain to the Pi-hole's allowlist or denylist with specified type and kind,
                    This may be either an exact domain or a regex, depending on kind.
                    - type: 'allow' to add to the allowlist, 'deny' to add to the denylist.
                    - kind: 'exact' for an exact domain match, 'regex' for a regular expression match.
                    """)
    public String addNewDomain(@ToolParam(description = "type of the list, can be allow or deny") String type,
                               @ToolParam(description = "kind of the domain name, can be exact or regex") String kind,
                               @ToolParam(description = "the domain name to be added, can be domain or regular expression") String domain,
                               @ToolParam(description = "an optional comment for the domain", required = false) String comment) {
        return domainManageService.addNewDomain(type, kind, domain, comment);
    }

}
