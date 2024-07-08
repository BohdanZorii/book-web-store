package mate.zorii.bookstore.mapper;

import mate.zorii.bookstore.config.MapperConfig;
import mate.zorii.bookstore.dto.book.BookResponseDto;
import mate.zorii.bookstore.dto.book.CreateOrUpdateBookRequestDto;
import mate.zorii.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookResponseDto toDto(Book book);

    Book toModel(CreateOrUpdateBookRequestDto requestDto);

    void updateBookFromDto(CreateOrUpdateBookRequestDto requestDto, @MappingTarget Book book);
}
