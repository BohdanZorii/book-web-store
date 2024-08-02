package mate.zorii.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import mate.zorii.bookstore.model.ShoppingCart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShoppingCartRepositoryTests {
    @Autowired
    private ShoppingCartRepository cartRepository;

    @Test
    @DisplayName("Find cart by absent user id")
    void findByUserId_AbsentUserId_ReturnsNull() {
        Long absentUserId = 2L;

        ShoppingCart actual = cartRepository.findByUserId(absentUserId);

        assertNull(actual);
    }

    @Test
    @DisplayName("Find cart with items and their books by present user id")
    void findByUserId_PresentUserId_ReturnsShoppingCart() {
        Long presentUserId = 1L;

        ShoppingCart actual = cartRepository.findByUserId(presentUserId);

        assertNotNull(actual);
        assertNotNull(actual.getCartItems());
        actual.getCartItems().forEach(item -> assertNotNull(item.getBook()));
    }
}
