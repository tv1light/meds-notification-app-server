// src/main/java/ru/tv1light/medsnotification/controller/ReleaseFormController.java
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.dto.request.ReleaseFormRequest;
import ru.tv1light.medsnotification.dto.response.ReleaseFormResponse;
import ru.tv1light.medsnotification.service.ReleaseFormService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/release-forms")
@RequiredArgsConstructor
public class ReleaseFormController {

    private final ReleaseFormService service;

    @GetMapping
    public ResponseEntity<List<ReleaseFormResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReleaseFormResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ReleaseFormResponse> create(@Valid @RequestBody ReleaseFormRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReleaseFormResponse> update(@PathVariable Long id,
                                                      @Valid @RequestBody ReleaseFormRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}