package org.maxim.weatherapp.utils;

import org.maxim.weatherapp.dto.UserServiceDTO;
import org.maxim.weatherapp.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class PasswordEncoderUtil {

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isPasswordHashMatch(UserServiceDTO user, Optional<User> foundUser) {
        return BCrypt.checkpw(user.getPassword(), foundUser.get().getPassword());
    }
}
