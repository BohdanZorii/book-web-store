package mate.zorii.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import mate.zorii.bookstore.model.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartItemRepositoryTests {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    @DisplayName("Find cart item by absent cart id")
    void findByIdAndShoppingCart_Id_AbsentCartId_ReturnsEmptyOptional() {
        Long presentCartItemId = 1L;
        Long absentCartId = 2L;

        Optional<CartItem> actual = cartItemRepository
                .findByIdAndShoppingCart_Id(presentCartItemId, absentCartId);

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Find cart item by absent item id")
    void findByIdAndShoppingCart_Id_AbsentItemId_ReturnsEmptyOptional() {
        Long absentCartItemId = 3L;
        Long presentCartId = 1L;

        Optional<CartItem> actual = cartItemRepository
                .findByIdAndShoppingCart_Id(absentCartItemId, presentCartId);

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Find cart item by present item id and cart id")
    void findByIdAndShoppingCart_Id_PresentItemIdAndCartId_ReturnsItemOptional() {
        Long presentCartItemId = 1L;
        Long presentCartId = 1L;
        Long expectedBookId = 1L;

        Optional<CartItem> actual = cartItemRepository
                .findByIdAndShoppingCart_Id(presentCartItemId, presentCartId);

        assertFalse(actual.isEmpty());
        assertNotNull(actual.get().getBook());
        assertEquals(expectedBookId, actual.get().getBook().getId());
    }

    @Test
    @DisplayName("Delete cart items by absent cart id")
    void deleteAllByShoppingCart_Id_AbsentCartId_DoesntThrowException() {
        Long absentCartId = 2L;

        assertDoesNotThrow(() -> cartItemRepository.deleteAllByShoppingCart_Id(absentCartId));
    }

    @Test
    @DisplayName("Delete cart items by present cart id")
    @Sql(scripts = "classpath:database/add-deleted-cart-items.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteAllByShoppingCart_Id_PresentCartId_DeletesAllItems() {
        Long presentCartId = 1L;

        cartItemRepository.deleteAllByShoppingCart_Id(presentCartId);

        assertEquals(0, cartItemRepository.countCartItemsByShoppingCart_Id(presentCartId));
    }

    @Test
    @DisplayName("Count cart items by absent cart id")
    void countCartItemsByShoppingCart_Id_AbsentCartId_ReturnsZero() {
        Long absentCartId = 2L;

        int actual = cartItemRepository.countCartItemsByShoppingCart_Id(absentCartId);

        assertEquals(0, actual);
    }

    @Test
    @DisplayName("Count cart items by present cart id")
    void countCartItemsByShoppingCart_Id_PresentCartId_ReturnsItemsNumber() {
        Long presentCartId = 1L;
        int expectedItemsNumber = 2;

        int actual = cartItemRepository.countCartItemsByShoppingCart_Id(presentCartId);

        assertEquals(expectedItemsNumber, actual);
    }
}
