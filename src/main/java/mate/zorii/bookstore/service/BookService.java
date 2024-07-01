package mate.zorii.bookstore.service;

import java.util.List;
import mate.zorii.bookstore.dto.BookDto;
import mate.zorii.bookstore.dto.BookSearchRequestDto;
import mate.zorii.bookstore.dto.CreateOrUpdateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateOrUpdateBookRequestDto requestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto update(Long id, CreateOrUpdateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchRequestDto requestDto);
}
