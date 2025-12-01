package me.jafarkhq.piholemcp.pihole.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record AddDomainResponse(
        List<Domain> domains,
        Processed processed,
        double took) {

    public record Domain(
            String domain,
            String unicode,
            String type,
            String kind,
            String comment,
            List<Integer> groups,
            boolean enabled,
            int id,
            @JsonProperty("date_added") int dateAdded,
            @JsonProperty("date_modified") int dateModified) {
    }

    public record Processed(
            List<Success> success,
            List<Error> errors) {
    }

    public record Success(String item) {
    }

    public record Error(String item, String error) {
    }
}
