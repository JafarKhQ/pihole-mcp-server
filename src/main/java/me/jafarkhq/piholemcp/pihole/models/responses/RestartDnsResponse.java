package me.jafarkhq.piholemcp.pihole.models.responses;


public record RestartDnsResponse(
        String status,
        double took) {
}
