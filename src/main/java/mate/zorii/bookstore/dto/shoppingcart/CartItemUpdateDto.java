package mate.zorii.bookstore.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemUpdateDto(
        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be at least 1")
        int quantity) {
}
