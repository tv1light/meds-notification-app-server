// src/main/java/ru/tv1light/medsnotification/service/ActiveSubstanceService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.dictionary.activesubstance.ActiveSubstance;
import ru.tv1light.medsnotification.domain.dictionary.activesubstance.ActiveSubstanceRepository;
import ru.tv1light.medsnotification.dto.request.ActiveSubstanceRequest;
import ru.tv1light.medsnotification.dto.response.ActiveSubstanceResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiveSubstanceService {

    private final ActiveSubstanceRepository repo;

    public List<ActiveSubstanceResponse> getAll() {
        return repo.findAll().stream()
                .map(s -> new ActiveSubstanceResponse(s.getId(), s.getName()))
                .toList();
    }

    public ActiveSubstanceResponse getById(Long id) {
        ActiveSubstance s = repo.findById(id)
                .orElseThrow(() -> new ApiException("Действующее вещество не найдено"));
        return new ActiveSubstanceResponse(s.getId(), s.getName());
    }

    public ActiveSubstanceResponse create(ActiveSubstanceRequest req) {
        if (repo.existsByName(req.getName())) {
            throw new ApiException("Действующее вещество уже существует");
        }
        ActiveSubstance saved = repo.save(
                ActiveSubstance.builder().name(req.getName()).build());
        return new ActiveSubstanceResponse(saved.getId(), saved.getName());
    }

    public ActiveSubstanceResponse update(Long id, ActiveSubstanceRequest req) {
        ActiveSubstance s = repo.findById(id)
                .orElseThrow(() -> new ApiException("Действующее вещество не найдено"));
        if (!s.getName().equals(req.getName()) && repo.existsByName(req.getName())) {
            throw new ApiException("Действующее вещество уже существует");
        }
        s.setName(req.getName());
        return new ActiveSubstanceResponse(s.getId(), s.getName());
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ApiException("Действующее вещество не найдено");
        repo.deleteById(id);
    }
}