// src/main/java/ru/tv1light/medsnotification/service/CourseService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.course.Course;
import ru.tv1light.medsnotification.domain.course.CourseRepository;
import ru.tv1light.medsnotification.domain.medication.Medication;
import ru.tv1light.medsnotification.domain.medication.MedicationRepository;
import ru.tv1light.medsnotification.domain.user.User;
import ru.tv1light.medsnotification.domain.user.UserRepository;
import ru.tv1light.medsnotification.dto.request.CourseRequest;
import ru.tv1light.medsnotification.dto.response.CourseResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repo;
    private final UserRepository userRepository;
    private final MedicationRepository medicationRepository;

    private CourseResponse toResponse(Course c) {
        return new CourseResponse(
                c.getId(),
                c.getUser().getId(),
                c.getUser().getLogin(),
                c.getMedication().getId(),
                c.getMedication().getTradeName(),
                c.getStartDate(),
                c.getEndDate(),
                c.getNotes(),
                c.getCreatedAt()
        );
    }

    public List<CourseResponse> getAllByUser(Long userId, String sort) {
        List<Course> list = "desc".equalsIgnoreCase(sort)
                ? repo.findAllByUserIdOrderByStartDateDesc(userId)
                : repo.findAllByUserIdOrderByStartDateAsc(userId);
        return list.stream().map(this::toResponse).toList();
    }

    public CourseResponse getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ApiException("Курс не найден")));
    }

    public CourseResponse create(CourseRequest req) {
        if (req.getEndDate() != null && req.getEndDate().isBefore(req.getStartDate())) {
            throw new ApiException("Дата окончания не может быть раньше даты начала");
        }
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ApiException("Пользователь не найден"));
        Medication medication = medicationRepository.findById(req.getMedicationId())
                .orElseThrow(() -> new ApiException("Препарат не найден"));

        Course saved = repo.save(Course.builder()
                .user(user)
                .medication(medication)
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .notes(req.getNotes())
                .build());
        return toResponse(saved);
    }

    public CourseResponse update(Long id, CourseRequest req) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new ApiException("Курс не найден"));
        if (req.getEndDate() != null && req.getEndDate().isBefore(req.getStartDate())) {
            throw new ApiException("Дата окончания не может быть раньше даты начала");
        }
        Medication medication = medicationRepository.findById(req.getMedicationId())
                .orElseThrow(() -> new ApiException("Препарат не найден"));

        c.setMedication(medication);
        c.setStartDate(req.getStartDate());
        c.setEndDate(req.getEndDate());
        c.setNotes(req.getNotes());
        return toResponse(c);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ApiException("Курс не найден");
        repo.deleteById(id);
    }
}