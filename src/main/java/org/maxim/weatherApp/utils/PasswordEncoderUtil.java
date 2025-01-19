package org.maxim.weatherApp.utils;

import lombok.experimental.UtilityClass;
import org.maxim.weatherApp.dto.UserServiceDTO;
import org.maxim.weatherApp.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@UtilityClass
public class PasswordEncoderUtil {

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isPasswordHashMatch(UserServiceDTO user, Optional<User> foundUser) {
        return BCrypt.checkpw(user.password(), foundUser.get().getPassword());
    }
}
