package me.jafarkhq.piholemcp.pihole.models.responses;


public record BlockingStatusResponse(
        String blocking,
        Integer timer,
        double took) {
}
