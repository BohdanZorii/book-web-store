package mate.zorii.bookstore.dto.shoppingcart;

import java.util.List;

public record ShoppingCartResponseDto(
        Long id,
        Long userId,
        List<CartItemDto> cartItems
) {
}
