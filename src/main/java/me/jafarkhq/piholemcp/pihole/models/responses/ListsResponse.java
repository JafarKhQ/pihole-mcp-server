package me.jafarkhq.piholemcp.pihole.models.responses;

import java.util.List;

public record ListsResponse(
        List<ListResponse> lists,
        Double took,
        Object processed) {
}
