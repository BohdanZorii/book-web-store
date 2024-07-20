package mate.zorii.bookstore.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.zorii.bookstore.dto.book.BookDto;
import mate.zorii.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import mate.zorii.bookstore.dto.book.BookSearchRequestDto;
import mate.zorii.bookstore.exception.EntityNotFoundException;
import mate.zorii.bookstore.mapper.BookMapper;
import mate.zorii.bookstore.model.Book;
import mate.zorii.bookstore.repository.BookRepository;
import mate.zorii.bookstore.repository.SpecificationProvider;
import mate.zorii.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationProvider specificationProvider;

    @Override
    public BookDto save(BookDto bookDto) {
        Book savedBook = bookRepository.save(bookMapper.toModel(bookDto));
        return bookMapper.toDto(savedBook);
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookDto findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No book found by id " + id));
    }

    @Override
    public BookDto update(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No book found by id " + id));
        bookMapper.updateBookFromDto(book, bookDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchRequestDto bookDto) {
        Specification<Book> spec = specificationProvider.getSpecification(bookDto);
        List<Book> books = bookRepository.findAll(spec);
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookResponseDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategories_Id(categoryId).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
