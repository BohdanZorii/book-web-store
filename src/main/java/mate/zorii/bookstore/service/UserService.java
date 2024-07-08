package mate.zorii.bookstore.service;

import java.util.Optional;
import mate.zorii.bookstore.dto.user.UserRegistrationRequestDto;
import mate.zorii.bookstore.dto.user.UserResponseDto;
import mate.zorii.bookstore.model.User;

public interface UserService {
    Optional<User> findByEmail(String email);

    UserResponseDto save(UserRegistrationRequestDto requestDto);
}
