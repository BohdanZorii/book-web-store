package mate.zorii.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import mate.zorii.bookstore.model.OrderItem;
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
public class OrderItemsRepositoryTest {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("Find order items by absent order id")
    void findAllByOrderId_AbsentOrderId_ReturnsEmptyList() {
        Long absentOrderId = 2L;

        Page<OrderItem> actual = orderItemRepository.findAllByOrderId(absentOrderId,
                Pageable.unpaged());

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Find order items by present order id with page size 1")
    void findAllByOrderId_PresentOrderId_ReturnsItemsPage() {
        Long presentOrderId = 1L;
        int pageSize = 1;

        Page<OrderItem> actual = orderItemRepository.findAllByOrderId(presentOrderId,
                Pageable.ofSize(pageSize));

        assertFalse(actual.isEmpty());
        assertEquals(pageSize, actual.getSize());
    }

    @Test
    @DisplayName("Find order item by absent order id")
    void findByIdAndOrder_Id_AbsentOrderId_ReturnsEmptyOptional() {
        Long presentOrderItemId = 1L;
        Long absentOrderId = 2L;

        Optional<OrderItem> actual = orderItemRepository
                .findByIdAndOrder_Id(presentOrderItemId, absentOrderId);

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Find order item by absent order item id")
    void findByIdAndOrder_Id_AbsentOrderItemId_ReturnsEmptyOptional() {
        Long absentOrderItemId = 4L;
        Long presentOrderId = 1L;

        Optional<OrderItem> actual = orderItemRepository
                .findByIdAndOrder_Id(absentOrderItemId, presentOrderId);

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Find order item by another order's id")
    @Sql(scripts = "classpath:database/add-10-orders-for-user-id-1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-added-orders.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByIdAndOrder_Id_AnotherOrderId_ReturnsEmptyOptional() {
        Long presentOrderItemId = 1L;
        Long anotherOrderId = 3L;

        Optional<OrderItem> actual = orderItemRepository
                .findByIdAndOrder_Id(presentOrderItemId, anotherOrderId);

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Find order item by present id and order id")
    void findByIdAndOrder_Id_PresentOrderItemIdAndOrderId_ReturnsOrderItemOptional() {
        Long presentOrderItemId = 1L;
        Long presentOrderId = 1L;

        Optional<OrderItem> actual = orderItemRepository
                .findByIdAndOrder_Id(presentOrderItemId, presentOrderId);

        assertTrue(actual.isPresent());
    }
}
