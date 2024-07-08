package mate.zorii.bookstore.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.user.UserRegistrationRequestDto;
import mate.zorii.bookstore.dto.user.UserResponseDto;
import mate.zorii.bookstore.exception.RegistrationException;
import mate.zorii.bookstore.model.User;
import mate.zorii.bookstore.service.AuthenticationService;
import mate.zorii.bookstore.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        Optional<User> user = userService.findByEmail(requestDto.email());
        if (user.isEmpty()) {
            return userService.save(requestDto);
        }
        throw new RegistrationException("Email is already registered");
    }
}
