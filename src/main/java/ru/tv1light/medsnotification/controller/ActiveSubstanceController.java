// src/main/java/ru/tv1light/medsnotification/controller/ActiveSubstanceController.java
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.dto.request.ActiveSubstanceRequest;
import ru.tv1light.medsnotification.dto.response.ActiveSubstanceResponse;
import ru.tv1light.medsnotification.service.ActiveSubstanceService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/active-substances")
@RequiredArgsConstructor
public class ActiveSubstanceController {

    private final ActiveSubstanceService service;

    @GetMapping
    public ResponseEntity<List<ActiveSubstanceResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiveSubstanceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ActiveSubstanceResponse> create(@Valid @RequestBody ActiveSubstanceRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActiveSubstanceResponse> update(@PathVariable Long id,
                                                          @Valid @RequestBody ActiveSubstanceRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}