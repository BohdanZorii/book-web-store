package mate.zorii.bookstore.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import mate.zorii.bookstore.model.Order;

public record OrderResponseDto(
        Long id,
        Long userId,
        List<OrderItemResponseDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Order.Status status
) {
}
