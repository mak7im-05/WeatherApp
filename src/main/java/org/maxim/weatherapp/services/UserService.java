package org.maxim.weatherapp.services;

import lombok.RequiredArgsConstructor;
import org.maxim.weatherapp.dto.UserServiceDTO;
import org.maxim.weatherapp.entities.User;
import org.maxim.weatherapp.mapper.IUserEntityMapper;
import org.maxim.weatherapp.repositories.UserRepository;
import org.maxim.weatherapp.utils.PasswordEncoderUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Transactional
    public void registerUser(UserServiceDTO userData) {
        String encryptedPassword = PasswordEncoderUtil.encryptPassword(userData.password());
        User user = userEntityMapper.mapTo(userData);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public int authenticateUser(UserServiceDTO user) {
        Optional<User> foundUser = userRepository.findByLogin(user.login());
        if (foundUser.isPresent() && PasswordEncoderUtil.isPasswordHashMatch(user, foundUser)) {
            return foundUser.get().getId();
        } else {
            throw new IllegalArgumentException("Invalid login or password");
        }
    }
}
