package me.jafarkhq.piholemcp.pihole.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.experimental.FieldDefaults;
import me.jafarkhq.piholemcp.pihole.models.responses.CreateListResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


@SpringBootTest
@EnableWireMock
@ActiveProfiles("test")
@FieldDefaults(makeFinal = false)
class ListManageServiceIntegrationTest {

    @InjectWireMock
    WireMockServer wireMockServer;

    @MockitoBean
    AuthService authService;

    @Autowired
    ListManageService listManageService;

    @BeforeEach
    void setupMockedAuthService() {
        when(authService.getValidToken())
                .thenReturn("test-sid");
    }

    @AfterEach
    void resetWireMock() {
        wireMockServer.resetAll();
    }

    @Test
    void addNewListShouldPostToPiHoleUsingWireMock() {
        wireMockServer.stubFor(post(urlPathEqualTo("/api/lists"))
                .withQueryParam("type", equalTo("allow"))
                .willReturn(okJson("""
                        {
                          "lists": [],
                          "processed": {
                            "success": [
                              { "item": "http://example.com" }
                            ],
                            "errors": []
                          },
                          "took": 0.12
                        }
                        """)));

        CreateListResponse response = listManageService.addNewList(
                "http://example.com", "ALLOW", " Test comment ", true
        );

        assertThat(response.processed().success())
                .extracting(CreateListResponse.ProcessedItem::item)
                .containsExactly("http://example.com");

        wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/lists"))
                .withHeader("X-FTL-SID", equalTo("test-sid"))
                .withQueryParam("type", equalTo("allow"))
                .withRequestBody(equalToJson("""
                        {
                          "address": ["http://example.com"],
                          "comment": "Test comment",
                          "groups": [0],
                          "enabled": true
                        }
                        """, true, true)));

        verify(authService, only()).getValidToken();
    }

}
