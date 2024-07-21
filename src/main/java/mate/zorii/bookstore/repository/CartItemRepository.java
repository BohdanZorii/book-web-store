package mate.zorii.bookstore.repository;

import java.util.Optional;
import mate.zorii.bookstore.model.CartItem;
import mate.zorii.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findCartItemByBook_IdAndShoppingCart(Long bookId, ShoppingCart shoppingCart);
}
