package mate.zorii.bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        Long id,
        @NotBlank(message = "Category name must not be blank")
        @Size(max = 100, message = "Category name must be less than 100 characters")
        String name,
        @Size(max = 2000, message = "Category description must be less than 2000 characters")
        String description
) {
}
