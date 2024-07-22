package mate.zorii.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.user.UserRegistrationRequestDto;
import mate.zorii.bookstore.dto.user.UserResponseDto;
import mate.zorii.bookstore.exception.RegistrationException;
import mate.zorii.bookstore.mapper.UserMapper;
import mate.zorii.bookstore.model.User;
import mate.zorii.bookstore.repository.UserRepository;
import mate.zorii.bookstore.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto bookDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(bookDto.email())) {
            throw new RegistrationException("Email is already registered");
        }
        User user = userMapper.toModel(bookDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
