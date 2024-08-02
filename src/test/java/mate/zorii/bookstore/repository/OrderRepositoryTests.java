package mate.zorii.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mate.zorii.bookstore.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Find all orders by absent user id")
    void findAllByUser_Id_AbsentUserId_ReturnEmptyPage() {
        Long absentUserId = 3L;

        Page<Order> actual = orderRepository.findAllByUser_Id(absentUserId, Pageable.unpaged());

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Find all orders by present user id with page size 5")
    @Sql(scripts = "classpath:database/add-10-orders-for-user-id-1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-added-orders.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByUser_Id_PresentUserIdAndPageableOfSize5_ReturnsPageOfSize5() {
        Long presentUserId = 1L;
        int pageSize = 5;

        Page<Order> actual = orderRepository.findAllByUser_Id(presentUserId, Pageable.ofSize(5));

        assertFalse(actual.isEmpty());
        assertEquals(pageSize, actual.getSize());
    }
}
