package me.jafarkhq.piholemcp.pihole.models.responses;

import java.util.List;

public record CreateListResponse(
        List<ListResponse> lists,
        ProcessedResponse processed,
        Double took) {

    public record ProcessedResponse(
            List<ProcessedItem> success,
            List<ProcessedError> errors) {
    }

    public record ProcessedItem(
            String item) {
    }

    public record ProcessedError(
            String item,
            String error) {
    }

}
