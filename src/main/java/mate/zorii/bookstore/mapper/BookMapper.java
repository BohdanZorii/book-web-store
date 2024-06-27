package mate.zorii.bookstore.mapper;

import mate.zorii.bookstore.config.MapperConfig;
import mate.zorii.bookstore.dto.BookDto;
import mate.zorii.bookstore.dto.CreateOrUpdateBookRequestDto;
import mate.zorii.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateOrUpdateBookRequestDto requestDto);
}
