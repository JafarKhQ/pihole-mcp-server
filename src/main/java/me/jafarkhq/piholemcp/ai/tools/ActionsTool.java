package me.jafarkhq.piholemcp.ai.tools;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.services.ActionsService;
import me.jafarkhq.piholemcp.pihole.services.DnsControlService;
import me.jafarkhq.piholemcp.pihole.services.DomainManageService;
import me.jafarkhq.piholemcp.pihole.services.ListManageService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


@Component
@RequiredArgsConstructor
public class ActionsTool {

    ObjectMapper objectMapper;

    ActionsService actionsService;
    DnsControlService dnsControlService;
    ListManageService listManageService;
    DomainManageService domainManageService;

    @Tool(name = "restart_pihole_service",
            description = "Restart the Pihole DNS service to apply changes or resolve issues.")
    public String restartFtlService() {
        return toJson(actionsService.restartDns());
    }

    @Tool(name = "get_pihole_dns_blocking_status",
            description = "Retrieve the current DNS blocking status of the Pihole server and the remaining seconds until blocking mode is automatically changed.")
    public String getBlockingStatus() {
        return toJson(dnsControlService.getCurrentStatus());
    }

    @Tool(name = "set_pihole_dns_blocking_status",
            description = "Enable or disable DNS blocking on the Pihole server. Optionally set a timer for how long to disable blocking.")
    public String setBlockingStatus(@ToolParam(description = "boolean true to enable DNS blocking or false to disable DNS blocking") boolean blocking,
                                    @ToolParam(description = "in case of disabling DNS blocking provide the period in seconds, otherwise send null", required = false) Integer seconds) {
        return toJson(dnsControlService.setCurrentStatus(blocking, seconds));
    }

    @Tool(name = "add_domain_to_pihole_deny_or_allow",
            description = """
                    Add a new domain to the Pihole's allow domain or deny domain with specified type and kind,
                    This may be either an exact domain or a regex, depending on kind.
                    - type: 'allow' to add to the allowlist, 'deny' to add to the denylist.
                    - kind: 'exact' for an exact domain match, 'regex' for a regular expression match.
                    """)
    public String addNewDomain(@ToolParam(description = "type of the list, can be allow or deny") String type,
                               @ToolParam(description = "kind of the domain name, can be exact or regex") String kind,
                               @ToolParam(description = "the domain name to be added, can be domain or regular expression") String domain,
                               @ToolParam(description = "an optional comment for the domain", required = false) String comment) {
        return toJson(domainManageService.addNewDomain(type, kind, domain, comment));
    }

    @Tool(name = "add_url_list_to_pihole_block_or_allow",
            description = """
                    Add a new url list to the Pihole's block list or allow list using a direct public URL.
                    - address: direct public url to list
                    - type: 'block' to add to the blocklist, 'allow' to add to the allowlist.
                    """)
    public String addNewList(@ToolParam(description = "direct public url to list") String url,
                             @ToolParam(description = "type of the list, can be block or allow") String type,
                             @ToolParam(description = "an optional comment for the new list", required = false) String comment) {
        return toJson(listManageService.addNewList(url, type, comment, true));
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

}
