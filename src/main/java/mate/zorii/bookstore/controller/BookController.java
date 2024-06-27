package mate.zorii.bookstore.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.zorii.bookstore.dto.BookDto;
import mate.zorii.bookstore.dto.CreateBookRequestDto;
import mate.zorii.bookstore.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDto create(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
}
