package me.jafarkhq.piholemcp.pihole.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ListResponse(
        String address,
        String comment,
        int[] groups,
        boolean enabled,
        Integer id,
        @JsonProperty("date_added")
        Long dateAdded,
        @JsonProperty("date_modified")
        Long dateModified,
        String type,
        @JsonProperty("date_updated")
        Long dateUpdated,
        Integer number,
        @JsonProperty("invalid_domains")
        Integer invalidDomains,
        @JsonProperty("abp_entries")
        Integer abpEntries,
        Integer status) {
}
