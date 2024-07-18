package mate.zorii.bookstore.service;

import java.util.List;
import mate.zorii.bookstore.dto.book.BookResponseDto;
import mate.zorii.bookstore.dto.book.BookSearchRequestDto;
import mate.zorii.bookstore.dto.book.CreateOrUpdateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(CreateOrUpdateBookRequestDto requestDto);

    Page<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto findById(Long id);

    BookResponseDto update(Long id, CreateOrUpdateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookResponseDto> search(BookSearchRequestDto requestDto);
}
