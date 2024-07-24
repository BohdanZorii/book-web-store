package mate.zorii.bookstore.repository;

import java.util.Optional;
import mate.zorii.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCart_Id(Long cartItemId, Long shoppingCartId);
}
