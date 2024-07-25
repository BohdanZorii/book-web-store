package mate.zorii.bookstore.service;

import java.util.Set;
import mate.zorii.bookstore.dto.shoppingcart.CartItemDto;
import mate.zorii.bookstore.dto.shoppingcart.CartItemUpdateDto;
import mate.zorii.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mate.zorii.bookstore.model.CartItem;
import mate.zorii.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto findByUserId(Long user);

    void addCartItem(CartItemDto cartItemDto, Long userId);

    ShoppingCartResponseDto updateCartItem(Long cartItemId, Long userId,
                                           CartItemUpdateDto cartItemUpdateDto);

    void deleteCartItem(Long id, Long userId);

    void createShoppingCart(User user);

    Set<CartItem> getCartItemsByUserId(Long id);

    void clearCart(Long userId);
}
