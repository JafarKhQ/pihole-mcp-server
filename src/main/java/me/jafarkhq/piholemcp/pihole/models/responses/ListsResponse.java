package me.jafarkhq.piholemcp.pihole.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record ListsResponse(
        List<ListResponse> lists,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        double took,
        Object processed) {
}
