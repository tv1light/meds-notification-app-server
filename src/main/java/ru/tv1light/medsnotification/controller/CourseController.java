// src/main/java/ru/tv1light/medsnotification/controller/CourseController.java
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.dto.request.CourseRequest;
import ru.tv1light.medsnotification.dto.response.CourseResponse;
import ru.tv1light.medsnotification.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    // GET /api/v1/courses?userId=1&sort=asc
    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllByUser(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "asc") String sort) {
        return ResponseEntity.ok(service.getAllByUser(userId, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody CourseRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}