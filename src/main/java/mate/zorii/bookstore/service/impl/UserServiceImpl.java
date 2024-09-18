package mate.zorii.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.user.UserRegistrationRequestDto;
import mate.zorii.bookstore.dto.user.UserResponseDto;
import mate.zorii.bookstore.exception.RegistrationException;
import mate.zorii.bookstore.mapper.UserMapper;
import mate.zorii.bookstore.model.User;
import mate.zorii.bookstore.repository.ShoppingCartRepository;
import mate.zorii.bookstore.repository.UserRepository;
import mate.zorii.bookstore.service.ShoppingCartService;
import mate.zorii.bookstore.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserMapper userMapper;
    private final ShoppingCartService shoppingCartService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(registrationDto.email())) {
            throw new RegistrationException("Email is already registered");
        }
        User user = userMapper.toModel(registrationDto);
        user.setPassword(passwordEncoder.encode(registrationDto.password()));
        userRepository.save(user);
        shoppingCartService.createShoppingCart(user);
        return userMapper.toDto(user);
    }
}
