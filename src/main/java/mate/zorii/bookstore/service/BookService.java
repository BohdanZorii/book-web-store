package mate.zorii.bookstore.service;

import java.util.List;
import mate.zorii.bookstore.dto.book.BookDto;
import mate.zorii.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import mate.zorii.bookstore.dto.book.BookSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(BookDto bookDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto update(Long id, BookDto bookDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchRequestDto bookDto);

    List<BookResponseDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId);
}
