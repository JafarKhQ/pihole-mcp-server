package me.jafarkhq.piholemcp.pihole.models.responses;

public record SessionResponse(boolean valid, String sid, int validity, String message) {
}
