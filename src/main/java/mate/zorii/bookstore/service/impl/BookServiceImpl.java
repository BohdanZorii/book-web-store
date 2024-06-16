package mate.zorii.bookstore.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.zorii.bookstore.dto.BookDto;
import mate.zorii.bookstore.dto.CreateBookRequestDto;
import mate.zorii.bookstore.exception.EntityNotFoundException;
import mate.zorii.bookstore.mapper.BookMapper;
import mate.zorii.bookstore.repository.BookRepository;
import mate.zorii.bookstore.service.BookService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
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
}
