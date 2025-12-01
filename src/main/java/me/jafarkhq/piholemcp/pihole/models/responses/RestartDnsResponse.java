package me.jafarkhq.piholemcp.pihole.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;


public record RestartDnsResponse(
        String status,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        double took) {
}
