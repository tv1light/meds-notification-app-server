// src/main/java/ru/tv1light/medsnotification/service/ManufacturerService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.dictionary.country.Country;
import ru.tv1light.medsnotification.domain.dictionary.country.CountryRepository;
import ru.tv1light.medsnotification.domain.dictionary.manufacturer.Manufacturer;
import ru.tv1light.medsnotification.domain.dictionary.manufacturer.ManufacturerRepository;
import ru.tv1light.medsnotification.dto.request.ManufacturerRequest;
import ru.tv1light.medsnotification.dto.response.ManufacturerResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository repo;
    private final CountryRepository countryRepository;

    private ManufacturerResponse toResponse(Manufacturer m) {
        return new ManufacturerResponse(
                m.getId(), m.getName(),
                m.getCountry().getId(), m.getCountry().getName());
    }

    public List<ManufacturerResponse> getAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public ManufacturerResponse getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ApiException("Производитель не найден")));
    }

    public ManufacturerResponse create(ManufacturerRequest req) {
        if (repo.existsByName(req.getName())) {
            throw new ApiException("Производитель уже существует");
        }
        Country country = countryRepository.findById(req.getCountryId())
                .orElseThrow(() -> new ApiException("Страна не найдена"));
        Manufacturer saved = repo.save(Manufacturer.builder()
                .name(req.getName()).country(country).build());
        return toResponse(saved);
    }

    public ManufacturerResponse update(Long id, ManufacturerRequest req) {
        Manufacturer m = repo.findById(id)
                .orElseThrow(() -> new ApiException("Производитель не найден"));
        if (!m.getName().equals(req.getName()) && repo.existsByName(req.getName())) {
            throw new ApiException("Производитель уже существует");
        }
        Country country = countryRepository.findById(req.getCountryId())
                .orElseThrow(() -> new ApiException("Страна не найдена"));
        m.setName(req.getName());
        m.setCountry(country);
        return toResponse(m);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ApiException("Производитель не найден");
        repo.deleteById(id);
    }
}