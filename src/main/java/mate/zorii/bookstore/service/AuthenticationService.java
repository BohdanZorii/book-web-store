package mate.zorii.bookstore.service;

import mate.zorii.bookstore.dto.user.UserLoginRequestDto;
import mate.zorii.bookstore.dto.user.UserLoginResponseDto;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto request);

    Long getAuthenticatedUserId(Authentication authentication);
}
