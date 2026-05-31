// src/main/java/ru/tv1light/medsnotification/service/ReminderService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.reminder.Reminder;
import ru.tv1light.medsnotification.domain.reminder.ReminderRepository;
import ru.tv1light.medsnotification.domain.reminder.ReminderStatus;
import ru.tv1light.medsnotification.domain.schedule.Schedule;
import ru.tv1light.medsnotification.domain.schedule.ScheduleRepository;
import ru.tv1light.medsnotification.dto.request.ReminderRequest;
import ru.tv1light.medsnotification.dto.response.ReminderResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository repo;
    private final ScheduleRepository scheduleRepository;

    private ReminderResponse toResponse(Reminder r) {
        return new ReminderResponse(
                r.getId(),
                r.getSchedule().getId(),
                r.getSchedule().getCourse().getId(),
                r.getSchedule().getCourse().getMedication().getTradeName(),
                r.getRemindAt(),
                r.getStatus()
        );
    }

    public List<ReminderResponse> getBySchedule(Long scheduleId) {
        return repo.findAllByScheduleIdOrderByRemindAtAsc(scheduleId)
                .stream().map(this::toResponse).toList();
    }

    public ReminderResponse getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ApiException("Напоминание не найдено")));
    }

    public ReminderResponse create(ReminderRequest req) {
        Schedule schedule = scheduleRepository.findById(req.getScheduleId())
                .orElseThrow(() -> new ApiException("Расписание не найдено"));
        Reminder saved = repo.save(Reminder.builder()
                .schedule(schedule)
                .remindAt(req.getRemindAt())
                .build());
        return toResponse(saved);
    }

    public ReminderResponse updateStatus(Long id, ReminderStatus status) {
        Reminder r = repo.findById(id)
                .orElseThrow(() -> new ApiException("Напоминание не найдено"));
        r.setStatus(status);
        return toResponse(r);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ApiException("Напоминание не найдено");
        repo.deleteById(id);
    }
}