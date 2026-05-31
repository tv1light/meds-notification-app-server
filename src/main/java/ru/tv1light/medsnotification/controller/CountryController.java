// src/main/java/ru/tv1light/medsnotification/controller/CountryController.java
package ru.tv1light.medsnotification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tv1light.medsnotification.dto.request.CountryRequest;
import ru.tv1light.medsnotification.dto.response.CountryResponse;
import ru.tv1light.medsnotification.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryResponse>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CountryResponse> create(@Valid @RequestBody CountryRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody CountryRequest req) {
        return ResponseEntity.ok(countryService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}