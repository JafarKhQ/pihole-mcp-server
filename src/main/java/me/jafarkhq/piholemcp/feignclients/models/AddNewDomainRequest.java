package me.jafarkhq.piholemcp.feignclients.models;

public record AddNewDomainRequest(String domain, String comment, int[] groups, boolean enabled) {
}
