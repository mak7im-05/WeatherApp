package org.maxim.weatherApp.services;

import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.request.UserServiceRequestDTO;
import org.maxim.weatherApp.entities.User;
import org.maxim.weatherApp.mapper.UserEntityMapper;
import org.maxim.weatherApp.repositories.UserRepository;
import org.maxim.weatherApp.utils.PasswordEncoderUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Transactional
    public void registerUser(UserServiceRequestDTO userData) {
        String encryptedPassword = PasswordEncoderUtil.encryptPassword(userData.password());

        User user = userEntityMapper.mapTo(userData);
        user.setPassword(encryptedPassword);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("this user has already been registered or something went wrong...");
        }
    }

    public int authenticateUser(UserServiceRequestDTO user) {
        Optional<User> foundUser = userRepository.findByLogin(user.login());
        if (foundUser.isPresent() && PasswordEncoderUtil.isPasswordHashMatch(user.password(), foundUser.get().getPassword())) {
            return foundUser.get().getId();
        } else {
            throw new IllegalArgumentException("Invalid login or password");
        }
    }

    public String getUserLoginById(int userId) {
        return userRepository.findById(userId).get().getLogin();
    }
}
