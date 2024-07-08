package mate.zorii.bookstore.service;

import mate.zorii.bookstore.dto.user.UserRegistrationRequestDto;
import mate.zorii.bookstore.dto.user.UserResponseDto;
import mate.zorii.bookstore.exception.RegistrationException;

public interface AuthenticationService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
