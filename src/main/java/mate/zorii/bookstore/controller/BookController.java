package mate.zorii.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.zorii.bookstore.dto.book.BookResponseDto;
import mate.zorii.bookstore.dto.book.BookSearchRequestDto;
import mate.zorii.bookstore.dto.book.CreateOrUpdateBookRequestDto;
import mate.zorii.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Tag(name = "Books API", description = "APIs for managing books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get all books", description = "Returns a list of all books")
    public Page<BookResponseDto> findAll(@Parameter(description =
            "Pageable object for pagination and sorting") Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user roles: " + auth.getAuthorities());
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get a book by ID", description = "Returns the book with the specified ID")
    public BookResponseDto findById(
            @Parameter(description = "ID of the book to retrieve", example = "1")
                                @PathVariable @PositiveOrZero Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Search books",
            description = "Searches books based on the provided criteria")
    public List<BookResponseDto> search(
            @Parameter(description = "Search criteria for filtering books")
            @RequestBody BookSearchRequestDto requestDto) {
        return bookService.search(requestDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create a new book", description = "Creates and returns a new book")
    public BookResponseDto create(
            @Parameter(description = "Request body containing book details")
            @RequestBody @Valid CreateOrUpdateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update a book by ID",
            description = "Updates and returns the updated book")
    public BookResponseDto update(
            @Parameter(description = "ID of the book to update", example = "1")
            @PathVariable @PositiveOrZero Long id,
            @Parameter(description = "Request body containing updated book details")
            @RequestBody @Valid CreateOrUpdateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a book by ID",
            description = "Deletes the book with the specified ID")
    public void delete(
            @Parameter(description = "ID of the book to delete", example = "1")
            @PathVariable @PositiveOrZero Long id) {
        bookService.deleteById(id);
    }
}
