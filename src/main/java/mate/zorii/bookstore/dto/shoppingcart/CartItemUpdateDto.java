package mate.zorii.bookstore.dto.shoppingcart;

import jakarta.validation.constraints.Positive;

public record CartItemUpdateDto(
        @Positive(message = "Quantity must be at least 1")
        int quantity) {
}
