# AI Instructions

## Project Overview
Project Name: Pihole MCP Server
Description: A Spring Boot stdio MCP Server interfaces with the Pihole API. expose Tools endpoints for AI interactions.
Tech Stack:
- Java 21, Maven build.
- Spring Boot 4.0.0
- Spring Cloud 25.0.0, FeignClient for Pihole API communication.
- Spring AI 2.0.0
- Jackson 3.x for JSON processing.
- Lombok for boilerplate code reduction, with `lombok.config` for project-wide settings.

## Architecture Guidelines
- Package root: `me.jafarkhq.piholemcp`.
    - Configuration layer: `configs` package, for application-wide configurations.
    - AI layer: `ai` package.
        - Tools layer: `tools` package.
    - Root Pihole Client layer: `pihole` package.
        - Pihole Client Feign layer: `clients` package.
        - Pihole Client Configuration layer: `configs` package, for Pihole Client specific configurations.
        - Pihole Client models (Requests/Responses) layer: `models.requests` and `models.responses` package.
        - Pihole Client Services layer: `services` package.

## Coding Guidelines
- Follow standard Java coding conventions.
- Use meaningful variable and method names.
- Write modular and reusable code.
- Use constructor injection (`@RequiredArgsConstructor` or explicit constructors) — no field injection.
- Use `var` only when the type is obvious.
- Use `@Slf4j` for logger injection, log at appropriate levels.
- Configuration properties in `application.yaml`.
- Lombok project defaults to `lombok.fieldDefaults.defaultFinal=true` and `lombok.fieldDefaults.defaultPrivate=true`.
- Use inner records when a record is only used once to reduce file count.
- Do not specify `private final` on fields — Lombok defaults handle this automatically.
- Use List over ArrayList, Map over HashMap, etc. for variable types.
- Use List instead of arrays for collections.

## Testing
- Use JUnit 5 + AssertJ + Mockito.
- Mocking with Mockito + `@ExtendWith(MockitoExtension.class)` or Spring Boot test slices.
- Test packages mirror source structure under `src/test/java`.
- 
## Avoid
- RestTemplate and WebClient. use FeignClient (PiholeClient).
- Deprecated Spring Boot APIs.
- Using `@Autowired` on fields.
- Hardcoding values. use configuration properties.
- Using `System.out.println` for logging.
