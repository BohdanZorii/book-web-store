package mate.zorii.bookstore.repository;

import java.util.Optional;
import mate.zorii.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
