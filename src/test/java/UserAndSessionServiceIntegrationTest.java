import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.maxim.weatherApp.config.SpringConfig;
import org.maxim.weatherApp.dto.request.UserServiceRequestDTO;
import org.maxim.weatherApp.entities.Session;
import org.maxim.weatherApp.entities.User;
import org.maxim.weatherApp.repositories.SessionRepository;
import org.maxim.weatherApp.repositories.UserRepository;
import org.maxim.weatherApp.services.SessionService;
import org.maxim.weatherApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SessionRepository ISessionRepository;
    @Autowired
    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        System.out.println();
        System.out.println();
    }

    @Test
    void UserRegistrationShouldAddNewUserInDatabase() {
        UserServiceRequestDTO user = new UserServiceRequestDTO("login@gmail.com", "password");

        userService.registerUser(user);

        Optional<User> savedUser = userRepository.findByLogin("login@gmail.com");
        System.out.println(savedUser);
        assertTrue(savedUser.isPresent());
        assertEquals(user.login(), savedUser.get().getLogin());
    }

    @Test
    void RegistrationWithDuplicateUsernameShouldThrowException() {
        UserServiceRequestDTO user = new UserServiceRequestDTO("login@gmail.com", "password");

        userService.registerUser(user);

        Optional<User> savedUser = userRepository.findByLogin("login@gmail.com");
        System.out.println(savedUser);
        assertTrue(savedUser.isPresent());
        assertEquals(user.login(), savedUser.get().getLogin());
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    void shouldExpireSessionAfterTimeout() {
        UserServiceRequestDTO user = new UserServiceRequestDTO("login@gmail.com", "password");
        userService.registerUser(user);
        int userId = userService.authenticateUser(user);

        UUID sessionUuid = sessionService.create(userId);
        Optional<Session> session = ISessionRepository.findById(sessionUuid);
        session.ifPresent(a -> a.setExpiresAt(LocalDateTime.now().minusSeconds(60 * 60 * 24)));
        session.ifPresent(a -> ISessionRepository.save(a));

        boolean sessionActive = sessionService.isSessionActive(sessionUuid);
        assertFalse(sessionActive);
    }

    @Test
    void shouldReturnUserIdOnSuccessfulLogin() {
        UserServiceRequestDTO user = new UserServiceRequestDTO("login@gmail.com", "password");

        userService.registerUser(user);

        int userId = userService.authenticateUser(user);
        int userIdFromRepository = userRepository.findById(userId).get().getId();

        assertEquals(userId, userIdFromRepository);
    }
}
