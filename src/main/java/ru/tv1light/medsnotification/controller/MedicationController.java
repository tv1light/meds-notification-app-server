// src/main/java/ru/tv1light/medsnotification/controller/MedicationController.java
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.dto.request.MedicationRequest;
import ru.tv1light.medsnotification.dto.response.MedicationResponse;
import ru.tv1light.medsnotification.service.MedicationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService service;

    // sort=asc (по умолчанию) или sort=desc — это первый из 2 видов сортировки по ТЗ
    @GetMapping
    public ResponseEntity<List<MedicationResponse>> getAll(
            @RequestParam(defaultValue = "asc") String sort) {
        return ResponseEntity.ok(service.getAll(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<MedicationResponse> create(@Valid @RequestBody MedicationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationResponse> update(@PathVariable Long id,
                                                     @Valid @RequestBody MedicationRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}