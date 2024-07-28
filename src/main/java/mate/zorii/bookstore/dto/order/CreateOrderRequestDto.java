package mate.zorii.bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequestDto(
        @NotBlank
        @Size(max = 255, message = "shippingAddress must be less than 256 characters")
        String shippingAddress) {
}
