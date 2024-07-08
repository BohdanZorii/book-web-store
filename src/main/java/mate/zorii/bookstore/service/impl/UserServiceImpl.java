package mate.zorii.bookstore.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.user.UserRegistrationRequestDto;
import mate.zorii.bookstore.dto.user.UserResponseDto;
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
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDto save(UserRegistrationRequestDto requestDto) {
        return userMapper.toDto(userRepository.save(userMapper.toModel(requestDto)));
    }
}
