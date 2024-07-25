package mate.zorii.bookstore.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.shoppingcart.CartItemDto;
import mate.zorii.bookstore.dto.shoppingcart.CartItemUpdateDto;
import mate.zorii.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mate.zorii.bookstore.mapper.ShoppingCartMapper;
import mate.zorii.bookstore.model.CartItem;
import mate.zorii.bookstore.model.ShoppingCart;
import mate.zorii.bookstore.model.User;
import mate.zorii.bookstore.repository.BookRepository;
import mate.zorii.bookstore.repository.CartItemRepository;
import mate.zorii.bookstore.repository.ShoppingCartRepository;
import mate.zorii.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper mapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ShoppingCartResponseDto findByUserId(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        return mapper.toShoppingCartDto(cart);
    }

    @Override
    @Transactional
    public void addCartItem(CartItemDto cartItemDto, Long userId) {
        if (!bookRepository.existsById(cartItemDto.bookId())) {
            throw new EntityNotFoundException("No book found with id " + cartItemDto.bookId());
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(cartItemDto.bookId()))
                .findFirst()
                .map(item -> {
                    item.setQuantity(item.getQuantity() + cartItemDto.quantity());
                    return item;
                }).orElse(mapper.toModel(cartItemDto, shoppingCart));

        cartItemRepository.save(cartItem);
        //ensure fresh data on the next select
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateCartItem(Long cartItemId, Long userId,
                                                  CartItemUpdateDto cartItemUpdateDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCart_Id(cartItemId, shoppingCart.getId())
                .map(item -> {
                    item.setQuantity(cartItemUpdateDto.quantity());
                    return item;
                }).orElseThrow(() -> new EntityNotFoundException(
                        String.format("No cart item with id: %d for user: %d",
                                cartItemId, userId)));
        cartItemRepository.save(cartItem);
        return mapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartItemId, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        cartItemRepository.findByIdAndShoppingCart_Id(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No cart item with id: %d for user: %d",
                                cartItemId, userId)));
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public Set<CartItem> getCartItemsByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId).getCartItems();
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        System.out.println("was here");//виводиться
        cartItemRepository.deleteAllByShoppingCart_Id(userId);
    }
}
