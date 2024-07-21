package mate.zorii.bookstore.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemDto(
        @NotNull(message = "Book ID cannot be null")
        @Positive(message = "Book ID must be positive")
        Long bookId,
        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be at least 1")
        int quantity
) {
}
