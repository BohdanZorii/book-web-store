package mate.zorii.bookstore.repository;

import java.util.Optional;
import mate.zorii.bookstore.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);

    Optional<OrderItem> findByIdAndOrder_Id(Long id, Long orderId);
}
