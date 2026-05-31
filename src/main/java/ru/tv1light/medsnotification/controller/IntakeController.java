// src/main/java/ru/tv1light/medsnotification/controller/IntakeController.java
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.dto.request.IntakeRequest;
import ru.tv1light.medsnotification.dto.response.IntakeResponse;
import ru.tv1light.medsnotification.service.IntakeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/intakes")
@RequiredArgsConstructor
public class IntakeController {

    private final IntakeService service;

    // GET /api/v1/intakes?userId=1&sort=desc  — журнал приёмов пользователя
    @GetMapping
    public ResponseEntity<List<IntakeResponse>> getByUser(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "desc") String sort) {
        return ResponseEntity.ok(service.getByUser(userId, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntakeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Подтвердить / пропустить / перенести приём
    @PostMapping
    public ResponseEntity<IntakeResponse> create(@Valid @RequestBody IntakeRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
}