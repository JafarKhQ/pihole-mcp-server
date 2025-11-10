package me.jafarkhq.piholemcp.feignclients.models;

public record ErrorResponse(String key, String message, String hint) {
}
