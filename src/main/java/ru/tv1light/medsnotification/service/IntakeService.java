// src/main/java/ru/tv1light/medsnotification/service/IntakeService.java
package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.intake.Intake;
import ru.tv1light.medsnotification.domain.intake.IntakeAction;
import ru.tv1light.medsnotification.domain.intake.IntakeRepository;
import ru.tv1light.medsnotification.domain.reminder.Reminder;
import ru.tv1light.medsnotification.domain.reminder.ReminderRepository;
import ru.tv1light.medsnotification.domain.reminder.ReminderStatus;
import ru.tv1light.medsnotification.domain.user.User;
import ru.tv1light.medsnotification.domain.user.UserRepository;
import ru.tv1light.medsnotification.dto.request.IntakeRequest;
import ru.tv1light.medsnotification.dto.response.IntakeResponse;
import ru.tv1light.medsnotification.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IntakeService {

    private final IntakeRepository repo;
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    private IntakeResponse toResponse(Intake i) {
        return new IntakeResponse(
                i.getId(),
                i.getReminder().getId(),
                i.getUser().getId(),
                i.getReminder().getSchedule().getCourse().getMedication().getTradeName(),
                i.getAction(),
                i.getActionedAt(),
                i.getSnoozedTo()
        );
    }

    public List<IntakeResponse> getByUser(Long userId, String sort) {
        List<Intake> list = "asc".equalsIgnoreCase(sort)
                ? repo.findAllByUserIdOrderByActionedAtAsc(userId)
                : repo.findAllByUserIdOrderByActionedAtDesc(userId);
        return list.stream().map(this::toResponse).toList();
    }

    public IntakeResponse getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ApiException("Запись о приёме не найдена")));
    }

    public IntakeResponse create(IntakeRequest req) {
        if (req.getAction() == IntakeAction.SNOOZED && req.getSnoozedTo() == null) {
            throw new ApiException("При переносе укажите snoozedTo");
        }
        Reminder reminder = reminderRepository.findById(req.getReminderId())
                .orElseThrow(() -> new ApiException("Напоминание не найдено"));
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ApiException("Пользователь не найден"));

        // Обновляем статус напоминания в соответствии с действием
        ReminderStatus newStatus = switch (req.getAction()) {
            case DONE    -> ReminderStatus.DONE;
            case SKIPPED -> ReminderStatus.SKIPPED;
            case SNOOZED -> ReminderStatus.SNOOZED;
        };
        reminder.setStatus(newStatus);
        reminderRepository.save(reminder);

        Intake saved = repo.save(Intake.builder()
                .reminder(reminder)
                .user(user)
                .action(req.getAction())
                .snoozedTo(req.getSnoozedTo())
                .build());
        return toResponse(saved);
    }
}