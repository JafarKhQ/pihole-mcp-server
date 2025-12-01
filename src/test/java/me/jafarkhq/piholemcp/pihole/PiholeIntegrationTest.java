package me.jafarkhq.piholemcp.pihole;

import lombok.experimental.FieldDefaults;
import me.jafarkhq.piholemcp.pihole.models.responses.StatusSummeryResponse;
import me.jafarkhq.piholemcp.pihole.services.MetricsService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("local")
@FieldDefaults(makeFinal = false)
@Disabled("Pihole Local IntegrationTest. for local testing only.")
public class PiholeIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MetricsService metricsService;

    @Test
    void testGetStatusSummary() {
        // GIVEN

        // WHEN
        StatusSummeryResponse response = metricsService.getStatusSummary();

        // THEN
        assertThat(response).isNotNull();
        assertThat(toJson(response)).doesNotContain("types", "status", "replies");
    }


    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON: " + e.getMessage(), e);
        }
    }

}
