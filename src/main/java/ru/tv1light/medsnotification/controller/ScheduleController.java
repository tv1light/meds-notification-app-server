// src/main/java/ru/tv1light/medsnotification/controller/ScheduleController.java
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.dto.request.ScheduleRequest;
import ru.tv1light.medsnotification.dto.response.ScheduleResponse;
import ru.tv1light.medsnotification.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    // GET /api/v1/schedules?courseId=1
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getByCourse(@RequestParam Long courseId) {
        return ResponseEntity.ok(service.getByCourse(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@Valid @RequestBody ScheduleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody ScheduleRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}