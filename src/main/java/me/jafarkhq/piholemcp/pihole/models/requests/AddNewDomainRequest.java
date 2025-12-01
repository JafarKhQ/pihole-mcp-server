package me.jafarkhq.piholemcp.pihole.models.requests;

import java.util.List;


public record AddNewDomainRequest(
        String domain,
        String comment,
        List<Integer> groups,
        boolean enabled) {
}
