package mate.zorii.bookstore.service;

import java.util.List;
import mate.zorii.bookstore.dto.BookDto;
import mate.zorii.bookstore.dto.BookSearchRequestDto;
import mate.zorii.bookstore.dto.CreateOrUpdateBookRequestDto;

public interface BookService {
    BookDto save(CreateOrUpdateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto update(Long id, CreateOrUpdateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchRequestDto requestDto);
}
