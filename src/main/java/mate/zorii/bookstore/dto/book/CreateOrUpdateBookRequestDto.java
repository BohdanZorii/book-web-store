package mate.zorii.bookstore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateOrUpdateBookRequestDto(
        @NotBlank
        @Size(max = 255, message = "Title must be less than 256 characters")
        String title,
        @NotBlank
        @Size(max = 255, message = "Author must be less than 256 characters")
        String author,
        @NotBlank
        @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "Invalid ISBN format")
        String isbn,
        @NotNull
        @Positive
        BigDecimal price,
        @Size(max = 2000, message = "Description must be less than 2000 characters")
        String description,
        @Size(max = 255, message = "Cover image URL must be less than 256 characters")
        String coverImage
) {
}