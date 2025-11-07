package me.jafarkhq.piholemcp.feignclients.models;

public record SessionResponse(boolean valid, byte[] sid, byte[] csrf, String message) {
}
