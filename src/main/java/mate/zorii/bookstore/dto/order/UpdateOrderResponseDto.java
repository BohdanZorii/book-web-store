package mate.zorii.bookstore.dto.order;

import mate.zorii.bookstore.model.Order;

public record UpdateOrderResponseDto(Long id, Order.Status status) {
}
