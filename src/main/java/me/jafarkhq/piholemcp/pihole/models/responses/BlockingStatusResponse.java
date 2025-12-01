package me.jafarkhq.piholemcp.pihole.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;


public record BlockingStatusResponse(
        String blocking,
        Integer timer,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        double took) {
}
