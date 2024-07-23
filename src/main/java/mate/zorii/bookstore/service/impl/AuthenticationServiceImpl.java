package mate.zorii.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.user.UserLoginRequestDto;
import mate.zorii.bookstore.dto.user.UserLoginResponseDto;
import mate.zorii.bookstore.model.User;
import mate.zorii.bookstore.security.JwtUtil;
import mate.zorii.bookstore.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }

    @Override
    public Long getAuthenticatedUserId(Authentication authentication) {
        User authenticatedUser = (User) authentication.getPrincipal();
        return authenticatedUser.getId();
    }
}
