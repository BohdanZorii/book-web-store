package mate.zorii.bookstore.service;

import mate.zorii.bookstore.dto.shoppingcart.CartItemDto;
import mate.zorii.bookstore.dto.shoppingcart.CartItemUpdateDto;
import mate.zorii.bookstore.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto findByUserId(Long user);

    CartItemDto addCartItem(CartItemDto cartItemDto, Long authenticatedUser);

    CartItemDto updateCartItem(Long cartItemId, CartItemUpdateDto cartItemUpdateDto);

    void deleteCartItem(Long id);
}
