package mate.zorii.bookstore.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.zorii.bookstore.dto.BookDto;
import mate.zorii.bookstore.dto.BookSearchRequestDto;
import mate.zorii.bookstore.dto.CreateOrUpdateBookRequestDto;
import mate.zorii.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public Page<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable @PositiveOrZero Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/search")
    public List<BookDto> search(@RequestBody BookSearchRequestDto requestDto) {
        return bookService.search(requestDto);
    }

    @PostMapping
    public BookDto create(@RequestBody @Valid CreateOrUpdateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("{id}")
    public BookDto update(@PathVariable @PositiveOrZero Long id,
                          @RequestBody @Valid CreateOrUpdateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable @PositiveOrZero Long id) {
        bookService.deleteById(id);
    }
}
