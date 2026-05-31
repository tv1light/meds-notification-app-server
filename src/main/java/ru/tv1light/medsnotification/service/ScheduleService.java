// src/main/java/ru/tv1light/medsnotification/service/ScheduleService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.course.Course;
import ru.tv1light.medsnotification.domain.course.CourseRepository;
import ru.tv1light.medsnotification.domain.schedule.Schedule;
import ru.tv1light.medsnotification.domain.schedule.ScheduleRepository;
import ru.tv1light.medsnotification.dto.request.ScheduleRequest;
import ru.tv1light.medsnotification.dto.response.ScheduleResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repo;
    private final CourseRepository courseRepository;

    private ScheduleResponse toResponse(Schedule s) {
        return new ScheduleResponse(
                s.getId(),
                s.getCourse().getId(),
                s.getCourse().getMedication().getTradeName(),
                s.getRemindTime(),
                s.getCreatedAt()
        );
    }

    public List<ScheduleResponse> getByCourse(Long courseId) {
        return repo.findAllByCourseIdOrderByRemindTimeAsc(courseId)
                .stream().map(this::toResponse).toList();
    }

    public ScheduleResponse getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ApiException("Расписание не найдено")));
    }

    public ScheduleResponse create(ScheduleRequest req) {
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new ApiException("Курс не найден"));
        Schedule saved = repo.save(Schedule.builder()
                .course(course)
                .remindTime(req.getRemindTime())
                .build());
        return toResponse(saved);
    }

    public ScheduleResponse update(Long id, ScheduleRequest req) {
        Schedule s = repo.findById(id)
                .orElseThrow(() -> new ApiException("Расписание не найдено"));
        s.setRemindTime(req.getRemindTime());
        return toResponse(s);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ApiException("Расписание не найдено");
        repo.deleteById(id);
    }
}