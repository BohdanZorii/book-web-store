package mate.zorii.bookstore.mapper;

import mate.zorii.bookstore.config.MapperConfig;
import mate.zorii.bookstore.dto.user.UserRegistrationRequestDto;
import mate.zorii.bookstore.dto.user.UserResponseDto;
import mate.zorii.bookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
