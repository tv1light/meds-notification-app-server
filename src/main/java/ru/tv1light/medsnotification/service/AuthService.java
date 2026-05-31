package ru.tv1light.medsnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tv1light.medsnotification.domain.user.User;
import ru.tv1light.medsnotification.domain.user.UserRepository;
import ru.tv1light.medsnotification.dto.request.LoginRequest;
import ru.tv1light.medsnotification.dto.request.RegisterRequest;
import ru.tv1light.medsnotification.dto.response.UserResponse;
import ru.tv1light.medsnotification.exception.ApiException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByLogin(req.getLogin())) {
            throw new ApiException("Логин уже занят");
        }
        User user = User.builder()
                .login(req.getLogin())
                .password(req.getPassword())
                .build();
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getLogin());
    }

    public UserResponse login(LoginRequest req) {
        User user = userRepository.findByLogin(req.getLogin())
                .orElseThrow(() -> new ApiException("Неверный логин или пароль"));
        if (!user.getPassword().equals(req.getPassword())) {
            throw new ApiException("Неверный логин или пароль");
        }
        return new UserResponse(user.getId(), user.getLogin());
    }
}
