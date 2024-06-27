package mate.zorii.bookstore.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.zorii.bookstore.dto.BookDto;
import mate.zorii.bookstore.dto.CreateOrUpdateBookRequestDto;
import mate.zorii.bookstore.exception.EntityNotFoundException;
import mate.zorii.bookstore.mapper.BookMapper;
import mate.zorii.bookstore.model.Book;
import mate.zorii.bookstore.repository.BookRepository;
import mate.zorii.bookstore.service.BookService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateOrUpdateBookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No book found by id " + id)));
    }

    @Override
    public BookDto update(Long id, CreateOrUpdateBookRequestDto requestDto) {
        if (bookRepository.existsById(id)) {
            Book book = bookMapper.toModel(requestDto);
            book.setId(id);
            return bookMapper.toDto(bookRepository.save(book));
        }
        throw new EntityNotFoundException("No book with id " + id + " found.");
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
