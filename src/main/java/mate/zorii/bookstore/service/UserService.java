package mate.zorii.bookstore.service;

import mate.zorii.bookstore.dto.user.UserRegistrationRequestDto;
import mate.zorii.bookstore.dto.user.UserResponseDto;
import mate.zorii.bookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;

}
