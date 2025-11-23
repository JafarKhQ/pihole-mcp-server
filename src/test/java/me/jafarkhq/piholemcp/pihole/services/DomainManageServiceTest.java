package me.jafarkhq.piholemcp.pihole.services;

import lombok.experimental.FieldDefaults;
import me.jafarkhq.piholemcp.pihole.clients.DomainManageClient;
import me.jafarkhq.piholemcp.pihole.models.requests.AddNewDomainRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("DomainManageService Tests")
@FieldDefaults(makeFinal = false)
class DomainManageServiceTest {

    @Mock
    DomainManageClient domainManageClient;
    @Mock
    GroupsService groupsService;

    @InjectMocks
    DomainManageService domainManageService;

    @Nested
    @DisplayName("addNewDomain - Happy Path Tests")
    class AddNewDomainHappyPath {

        @Test
        @DisplayName("Should successfully add domain with valid parameters")
        void shouldAddDomainWithValidParameters() {
            // GIVEN
            String type = "allow";
            String kind = "exact";
            String domain = "example.com";
            String comment = "Test domain";
            String expectedResponse = "Success";

            when(domainManageClient.addNewDomain(anyString(), anyString(), any(AddNewDomainRequest.class)))
                    .thenReturn(expectedResponse);

            // WHEN
            String result = domainManageService.addNewDomain(type, kind, domain, comment);

            // THEN
            assertThat(result).isEqualTo(expectedResponse);
            verify(domainManageClient, times(1)).addNewDomain(eq(type), eq(kind), any(AddNewDomainRequest.class));
        }

        @ParameterizedTest
        @CsvSource({
                "ALLOW, exact",
                "Allow, exact",
                "allow, exact",
                "DENY, exact",
                "Deny, exact",
                "deny, exact"
        })
        @DisplayName("Should normalize type to lowercase")
        void shouldNormalizeTypeToLowercase(String inputType, String kind) {
            // GIVEN
            String domain = "example.com";
            String comment = "Test domain";
            String expectedType = inputType.toLowerCase();

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN
            domainManageService.addNewDomain(inputType, kind, domain, comment);

            // THEN
            ArgumentCaptor<String> typeCaptor = ArgumentCaptor.forClass(String.class);
            verify(domainManageClient).addNewDomain(typeCaptor.capture(), anyString(), any());
            assertThat(typeCaptor.getValue()).isEqualTo(expectedType);
        }

        @ParameterizedTest
        @CsvSource({
                "allow, EXACT",
                "allow, Exact",
                "allow, exact",
                "allow, REGEX",
                "allow, Regex",
                "allow, regex"
        })
        @DisplayName("Should normalize kind to lowercase")
        void shouldNormalizeKindToLowercase(String type, String inputKind) {
            // GIVEN
            String domain = "example.com";
            String comment = "Test domain";
            String expectedKind = inputKind.toLowerCase();

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN
            domainManageService.addNewDomain(type, inputKind, domain, comment);

            // THEN
            ArgumentCaptor<String> kindCaptor = ArgumentCaptor.forClass(String.class);
            verify(domainManageClient).addNewDomain(anyString(), kindCaptor.capture(), any());
            assertThat(kindCaptor.getValue()).isEqualTo(expectedKind);
        }

        @ParameterizedTest
        @CsvSource({
                "'  example.com  ', example.com",
                "'example.com   ', example.com",
                "'   example.com', example.com",
                "'  test.co.uk  ', test.co.uk"
        })
        @DisplayName("Should trim domain whitespace")
        void shouldTrimDomainWhitespace(String inputDomain, String expectedDomain) {
            // GIVEN
            String type = "allow";
            String kind = "exact";
            String comment = "Test domain";

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN
            domainManageService.addNewDomain(type, kind, inputDomain, comment);

            // THEN
            ArgumentCaptor<AddNewDomainRequest> requestCaptor = ArgumentCaptor.forClass(AddNewDomainRequest.class);
            verify(domainManageClient).addNewDomain(anyString(), anyString(), requestCaptor.capture());
            assertThat(requestCaptor.getValue().domain()).isEqualTo(expectedDomain);
        }

        @ParameterizedTest
        @CsvSource({
                "deny, regex, malware\\\\.com, Block malware",
                "allow, exact, example.com, Test domain",
                "deny, exact, ads.com, Block ads"
        })
        @DisplayName("Should create correct request with domain and comment")
        void shouldCreateCorrectRequest(String type, String kind, String domain, String comment) {
            // GIVEN
            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN
            domainManageService.addNewDomain(type, kind, domain, comment);

            // THEN
            ArgumentCaptor<AddNewDomainRequest> requestCaptor = ArgumentCaptor.forClass(AddNewDomainRequest.class);
            verify(domainManageClient).addNewDomain(anyString(), anyString(), requestCaptor.capture());

            AddNewDomainRequest request = requestCaptor.getValue();
            assertThat(request)
                    .extracting("domain", "comment")
                    .containsExactly(domain, comment);
        }
    }

    @Nested
    @DisplayName("addNewDomain - Invalid Type Tests")
    class AddNewDomainInvalidType {

        @ParameterizedTest
        @ValueSource(strings = {"invalid", "block", "permit", "reject", "BLOCK", ""})
        @DisplayName("Should throw IllegalArgumentException for invalid types")
        void shouldThrowForInvalidType(String invalidType) {
            // GIVEN
            String kind = "exact";
            String domain = "example.com";
            String comment = "Test";

            // WHEN & THEN
            assertThatThrownBy(() -> domainManageService.addNewDomain(invalidType, kind, domain, comment))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Invalid type");

            verify(domainManageClient, never()).addNewDomain(any(), any(), any());
        }

        @ParameterizedTest
        @ValueSource(strings = {"ALLOW", "Allow", "Deny", "DENY"})
        @DisplayName("Should be case-insensitive for valid types")
        void shouldHandleAllCasesForValidTypes(String type) {
            // GIVEN
            String kind = "exact";
            String domain = "example.com";
            String comment = "Test";

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN & THEN
            assertThatCode(() -> domainManageService.addNewDomain(type, kind, domain, comment))
                    .doesNotThrowAnyException();

            verify(domainManageClient, times(1)).addNewDomain(any(), any(), any());
        }
    }

    @Nested
    @DisplayName("addNewDomain - Invalid Kind Tests")
    class AddNewDomainInvalidKind {

        @ParameterizedTest
        @ValueSource(strings = {"wildcard", "pattern", "glob", "simple", "WILDCARD", ""})
        @DisplayName("Should throw IllegalArgumentException for invalid kinds")
        void shouldThrowForInvalidKind(String invalidKind) {
            // GIVEN
            String type = "allow";
            String domain = "example.com";
            String comment = "Test";

            // WHEN & THEN
            assertThatThrownBy(() -> domainManageService.addNewDomain(type, invalidKind, domain, comment))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Invalid kind");

            verify(domainManageClient, never()).addNewDomain(any(), any(), any());
        }

        @ParameterizedTest
        @ValueSource(strings = {"EXACT", "Exact", "REGEX", "Regex"})
        @DisplayName("Should be case-insensitive for valid kinds")
        void shouldHandleAllCasesForValidKinds(String kind) {
            // GIVEN
            String type = "allow";
            String domain = "example.com";
            String comment = "Test";

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN & THEN
            assertThatCode(() -> domainManageService.addNewDomain(type, kind, domain, comment))
                    .doesNotThrowAnyException();

            verify(domainManageClient, times(1)).addNewDomain(any(), any(), any());
        }
    }

    @Nested
    @DisplayName("addNewDomain - Invalid Domain Tests")
    class AddNewDomainInvalidDomain {

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t", "\n"})
        @DisplayName("Should throw IllegalArgumentException for blank domains")
        void shouldThrowForBlankDomain(String blankDomain) {
            // GIVEN
            String type = "allow";
            String kind = "exact";
            String comment = "Test";

            // WHEN & THEN
            assertThatThrownBy(() -> domainManageService.addNewDomain(type, kind, blankDomain, comment))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Domain cannot be blank");

            verify(domainManageClient, never()).addNewDomain(any(), any(), any());
        }

        @Test
        @DisplayName("Should throw when domain is null")
        void shouldThrowForNullDomain() {
            // GIVEN
            String type = "allow";
            String kind = "exact";
            String comment = "Test";

            // WHEN & THEN
            assertThatThrownBy(() -> domainManageService.addNewDomain(type, kind, null, comment))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Domain cannot be blank");

            verify(domainManageClient, never()).addNewDomain(any(), any(), any());
        }
    }

    @Nested
    @DisplayName("addNewDomain - Multiple Validations Tests")
    class AddNewDomainMultipleValidations {

        @Test
        @DisplayName("Should validate type before kind")
        void shouldValidateTypeBeforeKind() {
            // GIVEN
            String invalidType = "invalid";
            String invalidKind = "invalid";
            String domain = "example.com";
            String comment = "Test";

            // WHEN & THEN
            assertThatThrownBy(() -> domainManageService.addNewDomain(invalidType, invalidKind, domain, comment))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Invalid type");

            verify(domainManageClient, never()).addNewDomain(any(), any(), any());
        }

        @Test
        @DisplayName("Should validate kind before domain")
        void shouldValidateKindBeforeDomain() {
            // GIVEN
            String type = "allow";
            String invalidKind = "invalid";
            String blankDomain = "";
            String comment = "Test";

            // WHEN & THEN
            assertThatThrownBy(() -> domainManageService.addNewDomain(type, invalidKind, blankDomain, comment))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Invalid kind");

            verify(domainManageClient, never()).addNewDomain(any(), any(), any());
        }
    }

    @Nested
    @DisplayName("addNewDomain - Edge Cases Tests")
    class AddNewDomainEdgeCases {

        @Test
        @DisplayName("Should handle null comment")
        void shouldHandleNullComment() {
            // GIVEN
            String type = "allow";
            String kind = "exact";
            String domain = "example.com";
            String comment = null;

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN
            String result = domainManageService.addNewDomain(type, kind, domain, comment);

            // THEN
            assertThat(result).isEqualTo("Success");
            ArgumentCaptor<AddNewDomainRequest> requestCaptor = ArgumentCaptor.forClass(AddNewDomainRequest.class);
            verify(domainManageClient).addNewDomain(anyString(), anyString(), requestCaptor.capture());
            assertThat(requestCaptor.getValue().comment()).isNull();
        }

        @Test
        @DisplayName("Should handle mixed case domain and preserve it")
        void shouldPreserveMixedCaseDomain() {
            // GIVEN
            String type = "allow";
            String kind = "exact";
            String domain = "Example.Com";
            String comment = "Test";

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN
            domainManageService.addNewDomain(type, kind, domain, comment);

            // THEN
            ArgumentCaptor<AddNewDomainRequest> requestCaptor = ArgumentCaptor.forClass(AddNewDomainRequest.class);
            verify(domainManageClient).addNewDomain(anyString(), anyString(), requestCaptor.capture());
            assertThat(requestCaptor.getValue().domain()).isEqualTo("Example.Com");
        }

        @Test
        @DisplayName("Should accept 'deny' as valid type")
        void shouldAcceptDenyType() {
            // GIVEN
            String type = "deny";
            String kind = "exact";
            String domain = "malware.com";
            String comment = "Block malware";

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN
            String result = domainManageService.addNewDomain(type, kind, domain, comment);

            // THEN
            assertThat(result).isEqualTo("Success");
            verify(domainManageClient, times(1)).addNewDomain(eq("deny"), anyString(), any());
        }

        @Test
        @DisplayName("Should accept 'regex' as valid kind")
        void shouldAcceptRegexKind() {
            // GIVEN
            String type = "allow";
            String kind = "regex";
            String domain = ".*\\.example\\.com";
            String comment = "Allow subdomains";

            when(domainManageClient.addNewDomain(any(), any(), any(AddNewDomainRequest.class)))
                    .thenReturn("Success");

            // WHEN
            String result = domainManageService.addNewDomain(type, kind, domain, comment);

            // THEN
            assertThat(result).isEqualTo("Success");
            verify(domainManageClient, times(1)).addNewDomain(anyString(), eq("regex"), any());
        }
    }

}

