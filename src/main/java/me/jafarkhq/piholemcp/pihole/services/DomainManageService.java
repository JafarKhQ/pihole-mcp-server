package me.jafarkhq.piholemcp.pihole.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.clients.DomainManageClient;
import me.jafarkhq.piholemcp.pihole.models.requests.AddNewDomainRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DomainManageService {

    private static final List<String> VALID_TYPES = List.of("allow", "deny");
    private static final List<String> VALID_KINDS = List.of("exact", "regex");

    DomainManageClient client;

    public String addNewDomain(String type, String kind, String domain, String comment) {
        type = StringUtils.toRootLowerCase(type);
        kind = StringUtils.toRootLowerCase(kind);
        domain = StringUtils.trimToEmpty(domain);

        if (!VALID_TYPES.contains(type)) {
            throw new IllegalArgumentException("Invalid type: " + type + ". Must be one of: " + VALID_TYPES);
        }
        if (!VALID_KINDS.contains(kind)) {
            throw new IllegalArgumentException("Invalid kind: " + kind + ". Must be one of: " + VALID_KINDS);
        }
        if (StringUtils.isBlank(domain)) {
            throw new IllegalArgumentException("Domain cannot be blank");
        }

        return client.addNewDomain(type, kind, new AddNewDomainRequest(domain, comment, new int[]{0}, true));
    }

}
