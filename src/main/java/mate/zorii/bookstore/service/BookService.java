package mate.zorii.bookstore.service;

import java.util.List;
import mate.zorii.bookstore.dto.BookDto;
import mate.zorii.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
