# Codeit Backend Assessment — System Rules

## Tech Stack & Architecture
- **Language:** Java 17+
- **Framework:** Spring Boot 3+ (Spring Web, Spring Data MongoDB)
- **Database:** MongoDB (NoSQL)
- **Dependencies:** Lombok, Jakarta Validation (`jakarta.validation.*`)
- **Patterns:** Clean layer separation: Controller (DTOs) -> Service -> Repository -> Model.

## Critical Build & Test Commands
Always check compilation using these commands before declaring a task complete:
- **Build & Compile:** `.\gradlew.bat build -x test` (Windows PowerShell)
- **Run local server:** `.\gradlew.bat bootRun` (Windows PowerShell)
- **Run tests:** `.\gradlew.bat test`

## NoSQL & MongoDB Design Conventions
- This is a MongoDB application, not an RDB. 
- **Prefer Embedding:** For tightly bound entities that are read together (e.g., Course -> Chapters -> Lectures), embed them as a `List<SubEntity>` inside a single Aggregate Root document. Do not create separate collections or joins unless entities have completely independent lifecycles.
- **IDs:** Use `String id` with `@Id` annotation for collection entry points.

## Code Quality Standards
- **Lombok Usage:** Always use `@Data`, `@RequiredArgsConstructor`, and `@Slf4j` to minimize Java boilerplate.
- **Input Validation:** Enforce input constraints on all request DTOs using `@Valid` and Jakarta validation annotations (`@NotBlank`, `@NotNull`, `@Size`, etc.).
- **Error Handling:** Avoid throwing raw exceptions to the client. Intercept errors using a central `@ControllerAdvice` structure and return uniform JSON error payloads:
  `{ "success": false, "error": "ERROR_CODE", "message": "Clear explanation" }`