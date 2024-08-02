package mate.zorii.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import mate.zorii.bookstore.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    private final String notExistingEmail = "notexistingemail@example.com";
    private final String existingEmail = "user@example.com";

    @Test
    @DisplayName("User exists by absent email")
    void existsByEmail_AbsentEmail_ReturnsFalse() {
        assertFalse(userRepository.existsByEmail(notExistingEmail));
    }

    @Test
    @DisplayName("User exists by present email")
    void existsByEmail_PresentEmail_ReturnsTrue() {
        assertTrue(userRepository.existsByEmail(existingEmail));
    }

    @Test
    @DisplayName("Find user by absent email")
    void findByEmail_AbsentEmail_ReturnsEmptyOptional() {
        assertTrue(userRepository.findByEmail(notExistingEmail).isEmpty());
    }

    @Test
    @DisplayName("Find user with roles by present email")
    void findByEmail_PresentEmail_ReturnsUserOptionalWithRoles() {
        String expectedFirstName = "John";
        Optional<User> actual = userRepository.findByEmail(existingEmail);

        assertTrue(actual.isPresent());
        assertNotNull(actual.get().getRoles());
        assertEquals(expectedFirstName, actual.get().getFirstName());
    }
}

