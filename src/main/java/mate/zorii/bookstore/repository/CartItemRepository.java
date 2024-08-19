package mate.zorii.bookstore.repository;

import java.util.Optional;
import mate.zorii.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = "book")
    Optional<CartItem> findByIdAndShoppingCart_Id(Long cartItemId, Long shoppingCartId);

    void deleteAllByShoppingCart_Id(Long shoppingCartId);

    int countCartItemsByShoppingCart_Id(Long shoppingCartId);
}
