package mate.zorii.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.shoppingcart.CartItemDto;
import mate.zorii.bookstore.dto.shoppingcart.CartItemUpdateDto;
import mate.zorii.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mate.zorii.bookstore.mapper.ShoppingCartMapper;
import mate.zorii.bookstore.model.CartItem;
import mate.zorii.bookstore.model.ShoppingCart;
import mate.zorii.bookstore.repository.CartItemRepository;
import mate.zorii.bookstore.repository.ShoppingCartRepository;
import mate.zorii.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper mapper;

    @Override
    public ShoppingCartResponseDto findByUserId(Long userId) {
        return mapper.toShoppingCartDto(getCartByUserId(userId));
    }

    @Override
    public CartItemDto addCartItem(CartItemDto cartItemDto, Long userId) {
        ShoppingCart shoppingCart = getCartByUserId(userId);
        Optional<CartItem> cartItemFromDb = cartItemRepository.findCartItemByBook_IdAndShoppingCart(
                cartItemDto.bookId(), shoppingCart);

        CartItem cartItem;
        if (cartItemFromDb.isPresent()) {
            cartItem = cartItemFromDb.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.quantity());
        } else {
            cartItem = mapper.toModel(cartItemDto, shoppingCart);
        }
        return mapper.toCartItemDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateCartItem(Long cartItemId, CartItemUpdateDto cartItemUpdateDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("No cart item by id " + cartItemId));
        mapper.updateCartItemFromDto(cartItem, cartItemUpdateDto);
        return mapper.toCartItemDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(Long id) {
        System.out.println("was here");
        cartItemRepository.deleteById(id);
    }

    private ShoppingCart getCartByUserId(Long userId) {
        return shoppingCartRepository.findShoppingCartByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No shopping cart by user id " + userId));
    }
}
