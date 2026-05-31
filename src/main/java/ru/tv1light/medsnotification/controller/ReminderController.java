// src/main/java/ru/tv1light/medsnotification/controller/ReminderController.java
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.domain.reminder.ReminderStatus;
import ru.tv1light.medsnotification.dto.request.ReminderRequest;
import ru.tv1light.medsnotification.dto.response.ReminderResponse;
import ru.tv1light.medsnotification.service.ReminderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService service;

    // GET /api/v1/reminders?scheduleId=1
    @GetMapping
    public ResponseEntity<List<ReminderResponse>> getBySchedule(@RequestParam Long scheduleId) {
        return ResponseEntity.ok(service.getBySchedule(scheduleId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ReminderResponse> create(@Valid @RequestBody ReminderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    // PATCH /api/v1/reminders/5/status?value=DONE
    @PatchMapping("/{id}/status")
    public ResponseEntity<ReminderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam ReminderStatus value) {
        return ResponseEntity.ok(service.updateStatus(id, value));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}