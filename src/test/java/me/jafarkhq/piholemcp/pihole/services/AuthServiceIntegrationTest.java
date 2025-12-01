package me.jafarkhq.piholemcp.pihole.services;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("AuthService Integration Tests")
class AuthServiceIntegrationTest {

    @Nested
    @SpringBootTest
    @ActiveProfiles("test")
    @FieldDefaults(makeFinal = false)
    @DisplayName("When auth is enabled (password configured)")
    class AuthEnabledTest {

        @Autowired
        ApplicationContext context;

        @Test
        @DisplayName("Application context should load successfully")
        void contextLoads() {
            assertThat(context).isNotNull();
        }

        @Test
        @DisplayName("AuthService bean should be created")
        void authServiceBeanShouldExist() {
            assertThat(context.containsBean("authService")).isTrue();
            assertThat(context.getBean(AuthService.class)).isNotNull();
        }

        @Nested
        @SpringBootTest
        @ActiveProfiles("test")
        @TestPropertySource(properties = "pihole.app_password=")
        @FieldDefaults(makeFinal = false)
        @DisplayName("When auth is disabled (no password)")
        class AuthDisabledTest {

            @Autowired
            ApplicationContext context;

            @Test
            @DisplayName("Application context should load successfully")
            void contextLoads() {
                assertThat(context).isNotNull();
            }

            @Test
            @DisplayName("AuthService bean should NOT be created")
            void authServiceBeanShouldNotExist() {
                assertThat(context.containsBean("authService")).isFalse();
            }
        }
    }

}
