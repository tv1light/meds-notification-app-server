// src/main/java/ru/tv1light/medsnotification/service/MedicationService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.dictionary.activesubstance.ActiveSubstance;
import ru.tv1light.medsnotification.domain.dictionary.activesubstance.ActiveSubstanceRepository;
import ru.tv1light.medsnotification.domain.dictionary.manufacturer.Manufacturer;
import ru.tv1light.medsnotification.domain.dictionary.manufacturer.ManufacturerRepository;
import ru.tv1light.medsnotification.domain.dictionary.releaseform.ReleaseForm;
import ru.tv1light.medsnotification.domain.dictionary.releaseform.ReleaseFormRepository;
import ru.tv1light.medsnotification.domain.medication.Medication;
import ru.tv1light.medsnotification.domain.medication.MedicationRepository;
import ru.tv1light.medsnotification.dto.request.MedicationRequest;
import ru.tv1light.medsnotification.dto.response.MedicationResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository repo;
    private final ActiveSubstanceRepository substanceRepo;
    private final ReleaseFormRepository releaseFormRepo;
    private final ManufacturerRepository manufacturerRepo;

    private MedicationResponse toResponse(Medication m) {
        return new MedicationResponse(
                m.getId(),
                m.getTradeName(),
                m.getActiveSubstance().getId(),
                m.getActiveSubstance().getName(),
                m.getReleaseForm().getId(),
                m.getReleaseForm().getName(),
                m.getManufacturer().getId(),
                m.getManufacturer().getName(),
                m.getManufacturer().getCountry().getName(),
                m.getDosageValue(),
                m.getDosageUnit()
        );
    }

    public List<MedicationResponse> getAll(String sort) {
        List<Medication> list = "desc".equalsIgnoreCase(sort)
                ? repo.findAllByOrderByTradeNameDesc()
                : repo.findAllByOrderByTradeNameAsc();
        return list.stream().map(this::toResponse).toList();
    }

    public MedicationResponse getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ApiException("Препарат не найден")));
    }

    public MedicationResponse create(MedicationRequest req) {
        ActiveSubstance substance = substanceRepo.findById(req.getActiveSubstanceId())
                .orElseThrow(() -> new ApiException("Действующее вещество не найдено"));
        ReleaseForm form = releaseFormRepo.findById(req.getReleaseFormId())
                .orElseThrow(() -> new ApiException("Форма выпуска не найдена"));
        Manufacturer manufacturer = manufacturerRepo.findById(req.getManufacturerId())
                .orElseThrow(() -> new ApiException("Производитель не найден"));

        Medication saved = repo.save(Medication.builder()
                .tradeName(req.getTradeName())
                .activeSubstance(substance)
                .releaseForm(form)
                .manufacturer(manufacturer)
                .dosageValue(req.getDosageValue())
                .dosageUnit(req.getDosageUnit())
                .build());
        return toResponse(saved);
    }

    public MedicationResponse update(Long id, MedicationRequest req) {
        Medication m = repo.findById(id)
                .orElseThrow(() -> new ApiException("Препарат не найден"));

        m.setTradeName(req.getTradeName());
        m.setActiveSubstance(substanceRepo.findById(req.getActiveSubstanceId())
                .orElseThrow(() -> new ApiException("Действующее вещество не найдено")));
        m.setReleaseForm(releaseFormRepo.findById(req.getReleaseFormId())
                .orElseThrow(() -> new ApiException("Форма выпуска не найдена")));
        m.setManufacturer(manufacturerRepo.findById(req.getManufacturerId())
                .orElseThrow(() -> new ApiException("Производитель не найден")));
        m.setDosageValue(req.getDosageValue());
        m.setDosageUnit(req.getDosageUnit());
        return toResponse(m);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ApiException("Препарат не найден");
        repo.deleteById(id);
    }
}