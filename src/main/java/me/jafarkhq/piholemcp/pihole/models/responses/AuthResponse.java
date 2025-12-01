package me.jafarkhq.piholemcp.pihole.models.responses;

public record AuthResponse(
        SessionResponse session,
        ErrorResponse error) {
}
