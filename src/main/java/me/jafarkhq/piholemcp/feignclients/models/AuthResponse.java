package me.jafarkhq.piholemcp.feignclients.models;

public record AuthResponse(SessionResponse session, ErrorResponse error) {
}
