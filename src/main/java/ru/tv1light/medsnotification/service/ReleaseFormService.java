// src/main/java/ru/tv1light/medsnotification/service/ReleaseFormService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.dictionary.releaseform.ReleaseForm;
import ru.tv1light.medsnotification.domain.dictionary.releaseform.ReleaseFormRepository;
import ru.tv1light.medsnotification.dto.request.ReleaseFormRequest;
import ru.tv1light.medsnotification.dto.response.ReleaseFormResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseFormService {

    private static final int MAX_RELEASE_FORMS = 25;

    private final ReleaseFormRepository repo;

    public List<ReleaseFormResponse> getAll() {
        return repo.findAll().stream()
                .map(f -> new ReleaseFormResponse(f.getId(), f.getName()))
                .toList();
    }

    public ReleaseFormResponse getById(Long id) {
        ReleaseForm f = repo.findById(id)
                .orElseThrow(() -> new ApiException("Форма выпуска не найдена"));
        return new ReleaseFormResponse(f.getId(), f.getName());
    }

    public ReleaseFormResponse create(ReleaseFormRequest req) {
        if (repo.count() >= MAX_RELEASE_FORMS) {
            throw new ApiException("Достигнут лимит форм выпуска (" + MAX_RELEASE_FORMS + ")");
        }
        if (repo.existsByName(req.getName())) {
            throw new ApiException("Форма выпуска уже существует");
        }
        ReleaseForm saved = repo.save(ReleaseForm.builder().name(req.getName()).build());
        return new ReleaseFormResponse(saved.getId(), saved.getName());
    }

    public ReleaseFormResponse update(Long id, ReleaseFormRequest req) {
        ReleaseForm f = repo.findById(id)
                .orElseThrow(() -> new ApiException("Форма выпуска не найдена"));
        if (!f.getName().equals(req.getName()) && repo.existsByName(req.getName())) {
            throw new ApiException("Форма выпуска уже существует");
        }
        f.setName(req.getName());
        return new ReleaseFormResponse(f.getId(), f.getName());
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ApiException("Форма выпуска не найдена");
        repo.deleteById(id);
    }
}