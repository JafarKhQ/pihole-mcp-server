package me.jafarkhq.piholemcp.pihole.models.requests;

public record AddNewDomainRequest(String domain, String comment, int[] groups, boolean enabled) {
}
