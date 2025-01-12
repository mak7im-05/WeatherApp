import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.maxim.weatherapp.config.SpringConfig;
import org.maxim.weatherapp.dto.UserServiceDTO;
import org.maxim.weatherapp.entities.Session;
import org.maxim.weatherapp.entities.User;
import org.maxim.weatherapp.repositories.SessionRepository;
import org.maxim.weatherapp.repositories.UserRepository;
import org.maxim.weatherapp.services.SessionService;
import org.maxim.weatherapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class UserAndSessionServiceIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        System.out.println();
        System.out.println();
    }

    @Test
    void UserRegistrationShouldAddNewUserInDatabase() {
        UserServiceDTO user = new UserServiceDTO("login@gmail.com", "password");

        userService.registerUser(user);

        Optional<User> savedUser = userRepository.findByLogin("login@gmail.com");
        System.out.println(savedUser);
        assertTrue(savedUser.isPresent());
        assertEquals(user.login(), savedUser.get().getLogin());
    }

    @Test
    void RegistrationWithDuplicateUsernameShouldThrowException() {
        UserServiceDTO user = new UserServiceDTO("login@gmail.com", "password");

        userService.registerUser(user);

        Optional<User> savedUser = userRepository.findByLogin("login@gmail.com");
        System.out.println(savedUser);
        assertTrue(savedUser.isPresent());
        assertEquals(user.login(), savedUser.get().getLogin());
        assertThrows(DataIntegrityViolationException.class, () -> userService.registerUser(user));
    }

    @Test
    void shouldExpireSessionAfterTimeout() {
        UserServiceDTO user = new UserServiceDTO("login@gmail.com", "password");
        userService.registerUser(user);
        int userId = userService.authenticateUser(user);

        UUID sessionUuid = sessionService.create(userId);
        Optional<Session> session = sessionRepository.findById(sessionUuid);
        session.ifPresent(a -> a.setExpiresAt(LocalDateTime.now().minusSeconds(60*60*24)));
        session.ifPresent(a -> sessionRepository.save(a));

        boolean sessionActive = sessionService.isSessionActive(sessionUuid.toString());
        assertFalse(sessionActive);
    }

    @Test
    void shouldReturnUserIdOnSuccessfulLogin() {
        UserServiceDTO user = new UserServiceDTO("login@gmail.com", "password");

        userService.registerUser(user);

        int userId = userService.authenticateUser(user);
        int userIdFromRepository = userRepository.findById(userId).get().getId();

        assertEquals(userId, userIdFromRepository);
    }
}
