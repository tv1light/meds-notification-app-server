// src/main/java/ru/tv1light/medsnotification/service/CountryService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.dictionary.country.Country;
import ru.tv1light.medsnotification.domain.dictionary.country.CountryRepository;
import ru.tv1light.medsnotification.dto.request.CountryRequest;
import ru.tv1light.medsnotification.dto.response.CountryResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public List<CountryResponse> getAll() {
        return countryRepository.findAll().stream()
                .map(c -> new CountryResponse(c.getId(), c.getName()))
                .toList();
    }

    public CountryResponse getById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ApiException("Страна не найдена"));
        return new CountryResponse(country.getId(), country.getName());
    }

    public CountryResponse create(CountryRequest req) {
        if (countryRepository.existsByName(req.getName())) {
            throw new ApiException("Страна с таким названием уже существует");
        }
        Country saved = countryRepository.save(
                Country.builder().name(req.getName()).build());
        return new CountryResponse(saved.getId(), saved.getName());
    }

    public CountryResponse update(Long id, CountryRequest req) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ApiException("Страна не найдена"));
        if (!country.getName().equals(req.getName())
                && countryRepository.existsByName(req.getName())) {
            throw new ApiException("Страна с таким названием уже существует");
        }
        country.setName(req.getName());
        return new CountryResponse(country.getId(), country.getName());
    }

    public void delete(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new ApiException("Страна не найдена");
        }
        countryRepository.deleteById(id);
    }
}