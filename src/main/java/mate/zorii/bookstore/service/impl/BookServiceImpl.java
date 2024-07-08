package mate.zorii.bookstore.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.zorii.bookstore.dto.book.BookResponseDto;
import mate.zorii.bookstore.dto.book.BookSearchRequestDto;
import mate.zorii.bookstore.dto.book.CreateOrUpdateBookRequestDto;
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
    public BookResponseDto save(CreateOrUpdateBookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public Page<BookResponseDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookResponseDto findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No book found by id " + id));
    }

    @Override
    public BookResponseDto update(Long id, CreateOrUpdateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()
                        -> new EntityNotFoundException("No book found by id " + id));
        bookMapper.updateBookFromDto(requestDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookResponseDto> search(BookSearchRequestDto requestDto) {
        Specification<Book> spec = specificationProvider.getSpecification(requestDto);
        List<Book> books = bookRepository.findAll(spec);
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
