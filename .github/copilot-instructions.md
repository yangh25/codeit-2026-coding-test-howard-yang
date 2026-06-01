# Coding Standards
- Always write clean, production-grade Java 17+ and Spring Boot 3+ code.
- Use modern Jakarta namespaces (`jakarta.validation.*`) instead of legacy `javax` packages.
- Maximize the use of Lombok (`@Data`, `@RequiredArgsConstructor`, `@Slf4j`) to keep code concise.

# Architecture & Database
- This is a MongoDB NoSQL application. Prioritize Document Embedding (Aggregate Root pattern) over separate collections with manual references unless entities have independent lifecycles.
- Always implement clean layer separation: Controller (DTOs) -> Service -> Repository -> Model.
- Ensure all input endpoints utilize strict validation constraints (`@Valid`, `@NotBlank`, etc.).
- Include global exception handling and return structured, standardized JSON error responses.