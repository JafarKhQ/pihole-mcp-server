package me.jafarkhq.piholemcp.feignclients.models;

public record BlockingStatusRequest(boolean blocking, Integer timer) {
}
