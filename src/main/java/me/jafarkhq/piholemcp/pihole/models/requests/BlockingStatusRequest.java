package me.jafarkhq.piholemcp.pihole.models.requests;

public record BlockingStatusRequest(boolean blocking, Integer timer) {
}
