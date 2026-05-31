#!/bin/bash
# ============================================================
# Запустить из корня проекта:
#   chmod +x create_structure.sh && ./create_structure.sh
# ============================================================

BASE="src/main/java/ru/tv1light/medsnotification"
RES="src/main/resources"
TEST="src/test/java/ru/tv1light/medsnotification"

# ── Все директории ──────────────────────────────────────────
dirs=(
    "$BASE/domain/user"
    "$BASE/domain/medication"
    "$BASE/domain/course"
    "$BASE/domain/schedule"
    "$BASE/domain/intake"
    "$BASE/domain/reminder"
    "$BASE/domain/dictionary/country"
    "$BASE/domain/dictionary/manufacturer"
    "$BASE/domain/dictionary/releaseform"
    "$BASE/domain/dictionary/dosage"
    "$BASE/domain/dictionary/activesubstance"
    "$BASE/service"
    "$BASE/controller"
    "$BASE/dto/request"
    "$BASE/dto/response"
    "$BASE/dto/mapper"
    "$BASE/config"
    "$BASE/exception"
    "$BASE/notification"
    "$BASE/scheduler"
    "$RES/db/migration"
    "$TEST/service"
    "$TEST/controller"
)

for d in "${dirs[@]}"; do
    mkdir -p "$d"
    echo "  [+] $d"
done

# ── Файлы ────────────────────────────────────────────────────

# ---------- Main ----------
cat > "$BASE/MedsNotificationApplication.java" << 'EOF'
package ru.tv1light.medsnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedsNotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedsNotificationApplication.class, args);
    }
}
EOF

# ---------- User entity ----------
cat > "$BASE/domain/user/User.java" << 'EOF'
package ru.tv1light.medsnotification.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = "login"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Логин: 4..12 символов */
    @Column(nullable = false, length = 12, unique = true)
    @Size(min = 4, max = 12)
    private String login;

    /** Пароль: 8..14 символов (plain-text для MVP) */
    @Column(nullable = false, length = 14)
    @Size(min = 8, max = 14)
    private String password;
}
EOF

# ---------- User repository ----------
cat > "$BASE/domain/user/UserRepository.java" << 'EOF'
package ru.tv1light.medsnotification.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
}
EOF

# ---------- AuthService ----------
cat > "$BASE/service/AuthService.java" << 'EOF'
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.user.User;
import ru.tv1light.medsnotification.domain.user.UserRepository;
import ru.tv1light.medsnotification.dto.request.LoginRequest;
import ru.tv1light.medsnotification.dto.request.RegisterRequest;
import ru.tv1light.medsnotification.dto.response.UserResponse;
import ru.tv1light.medsnotification.exception.ApiException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByLogin(req.getLogin())) {
            throw new ApiException("Логин уже занят");
        }
        User user = User.builder()
                .login(req.getLogin())
                .password(req.getPassword())
                .build();
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getLogin());
    }

    public UserResponse login(LoginRequest req) {
        User user = userRepository.findByLogin(req.getLogin())
                .orElseThrow(() -> new ApiException("Неверный логин или пароль"));
        if (!user.getPassword().equals(req.getPassword())) {
            throw new ApiException("Неверный логин или пароль");
        }
        return new UserResponse(user.getId(), user.getLogin());
    }
}
EOF

# ---------- LoginRequest ----------
cat > "$BASE/dto/request/LoginRequest.java" << 'EOF'
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    @Size(min = 4, max = 12)
    private String login;

    @NotBlank
    @Size(min = 8, max = 14)
    private String password;
}
EOF

# ---------- RegisterRequest ----------
cat > "$BASE/dto/request/RegisterRequest.java" << 'EOF'
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    @Size(min = 4, max = 12)
    private String login;

    @NotBlank
    @Size(min = 8, max = 14)
    private String password;
}
EOF

# ---------- UserResponse ----------
cat > "$BASE/dto/response/UserResponse.java" << 'EOF'
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String login;
}
EOF

# ---------- AuthController ----------
cat > "$BASE/controller/AuthController.java" << 'EOF'
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.dto.request.LoginRequest;
import ru.tv1light.medsnotification.dto.request.RegisterRequest;
import ru.tv1light.medsnotification.dto.response.UserResponse;
import ru.tv1light.medsnotification.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
EOF

# ---------- ApiException ----------
cat > "$BASE/exception/ApiException.java" << 'EOF'
package ru.tv1light.medsnotification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
EOF

# ---------- ErrorResponse ----------
cat > "$BASE/exception/ErrorResponse.java" << 'EOF'
package ru.tv1light.medsnotification.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
}
EOF

# ---------- GlobalExceptionHandler ----------
cat > "$BASE/exception/GlobalExceptionHandler.java" << 'EOF'
package ru.tv1light.medsnotification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApi(ApiException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(msg));
    }
}
EOF

# ---------- SecurityConfig ----------
cat > "$BASE/config/SecurityConfig.java" << 'EOF'
package ru.tv1light.medsnotification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** MVP: все эндпоинты открыты, CSRF отключён */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
EOF

# ---------- OpenApiConfig ----------
cat > "$BASE/config/OpenApiConfig.java" << 'EOF'
package ru.tv1light.medsnotification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Meds Notification API")
                        .version("1.0")
                        .description("MVP — напоминания о приёме лекарств"));
    }
}
EOF

# ---------- NotificationService ----------
cat > "$BASE/notification/NotificationService.java" << 'EOF'
package ru.tv1light.medsnotification.notification;

public interface NotificationService {
    void sendReminder(Long userId, String title, String body);
}
EOF

cat > "$BASE/notification/LocalNotificationStub.java" << 'EOF'
package ru.tv1light.medsnotification.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/** Profile=local: сервер только логирует, мобилка сама триггерит */
@Slf4j
@Service
@Profile({"local", "default"})
public class LocalNotificationStub implements NotificationService {

    @Override
    public void sendReminder(Long userId, String title, String body) {
        log.info("[LOCAL STUB] userId={} | {} — {}", userId, title, body);
    }
}
EOF

cat > "$BASE/notification/FcmNotificationStub.java" << 'EOF'
package ru.tv1light.medsnotification.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/** Profile=fcm: здесь будет Firebase Admin SDK */
@Slf4j
@Service
@Profile("fcm")
public class FcmNotificationStub implements NotificationService {

    @Override
    public void sendReminder(Long userId, String title, String body) {
        // TODO: FirebaseMessaging.getInstance().send(...)
        log.info("[FCM STUB] userId={} | {} — {}", userId, title, body);
    }
}
EOF

# ---------- application.yml ----------
cat > "$RES/application.yml" << 'EOF'
spring:
  application:
    name: meds-notification-app-server

  datasource:
    url: jdbc:postgresql://localhost:5432/meds_notification
    username: postgres
    password: postgres

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: true

  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true

springdoc:
  swagger-ui:
    path: /swagger-ui.html

logging:
  level:
    ru.tv1light.medsnotification: DEBUG
EOF

# ---------- Flyway V1 ----------
cat > "$RES/db/migration/V1__init_schema.sql" << 'EOF'
-- V1__init_schema.sql

CREATE TABLE IF NOT EXISTS users (
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(14) NOT NULL,
    CONSTRAINT chk_login_len    CHECK (char_length(login)    BETWEEN 4 AND 12),
    CONSTRAINT chk_password_len CHECK (char_length(password) BETWEEN 8 AND 14)
);

CREATE TABLE IF NOT EXISTS countries (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(56) NOT NULL UNIQUE,
    CONSTRAINT chk_country_len CHECK (char_length(name) BETWEEN 3 AND 56)
);

CREATE TABLE IF NOT EXISTS manufacturers (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL UNIQUE,
    country_id BIGINT REFERENCES countries(id),
    CONSTRAINT chk_manufacturer_len CHECK (char_length(name) BETWEEN 2 AND 50)
);

CREATE TABLE IF NOT EXISTS active_substances (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT chk_substance_len CHECK (char_length(name) BETWEEN 1 AND 50)
);

CREATE TABLE IF NOT EXISTS release_forms (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT chk_form_len CHECK (char_length(name) BETWEEN 2 AND 50)
);

CREATE TABLE IF NOT EXISTS medications (
    id                  BIGSERIAL PRIMARY KEY,
    trade_name          VARCHAR(50) NOT NULL,
    active_substance_id BIGINT REFERENCES active_substances(id),
    release_form_id     BIGINT REFERENCES release_forms(id),
    manufacturer_id     BIGINT REFERENCES manufacturers(id),
    dosage_value        NUMERIC(10,3) NOT NULL,
    dosage_unit         VARCHAR(10)   NOT NULL,
    CONSTRAINT chk_med_name_len CHECK (char_length(trade_name) BETWEEN 2 AND 50)
);
EOF

echo ""
echo "======================================================"
echo "  Структура создана!"
echo "  1. Замени pom.xml в корне репо"
echo "  2. Создай БД: CREATE DATABASE meds_notification;"
echo "  3. IntelliJ -> Load Maven Changes"
echo "  4. Run -> Swagger: http://localhost:8080/swagger-ui.html"
echo "======================================================"
