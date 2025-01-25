package org.maxim.weatherApp.service;

import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.request.UserServiceRequestDto;
import org.maxim.weatherApp.entity.User;
import org.maxim.weatherApp.repository.UserRepository;
import org.maxim.weatherApp.util.PasswordEncoderUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void registerUser(User user) {
        String encryptedPassword = PasswordEncoderUtil.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("this user has already been registered or something went wrong...");
        }
    }

    @Transactional(readOnly = true)
    public int authenticateUser(UserServiceRequestDto user) {
        return userRepository.findByLogin(user.login()).filter(
                        foundUser -> PasswordEncoderUtil.isPasswordHashMatch(user.password(), foundUser.getPassword())
                ).orElseThrow(() -> new IllegalArgumentException("Invalid login or password"))
                .getId();
    }

    @Transactional(readOnly = true)
    public String getUserLoginById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Session not found"))
                .getLogin();
    }
}
