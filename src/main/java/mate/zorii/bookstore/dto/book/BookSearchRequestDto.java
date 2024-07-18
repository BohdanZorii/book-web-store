package mate.zorii.bookstore.dto.book;

import jakarta.validation.constraints.Size;

public record BookSearchRequestDto(
        @Size(max = 255, message = "Title must be less than 256 characters")
        String title,
        @Size(max = 255, message = "Author must be less than 256 characters")
        String author) {
}
