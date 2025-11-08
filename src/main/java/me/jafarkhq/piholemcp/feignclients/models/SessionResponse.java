package me.jafarkhq.piholemcp.feignclients.models;

public record SessionResponse(boolean valid, String sid, int validity, String message) {
}
