package mate.zorii.bookstore.service;

import mate.zorii.bookstore.dto.user.UserLoginRequestDto;
import mate.zorii.bookstore.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto request);
}
