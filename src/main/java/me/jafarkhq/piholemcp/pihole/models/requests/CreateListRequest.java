package me.jafarkhq.piholemcp.pihole.models.requests;

import java.util.List;


public record CreateListRequest(
        List<String> address,
        String comment,
        List<Integer> groups,
        boolean enabled
) {
}
