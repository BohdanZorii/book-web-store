package mate.zorii.bookstore.service;

import mate.zorii.bookstore.dto.order.CreateOrderRequestDto;
import mate.zorii.bookstore.dto.order.OrderItemResponseDto;
import mate.zorii.bookstore.dto.order.OrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto placeOrder(CreateOrderRequestDto requestDto, Long authenticatedUserId);

    UpdateOrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusDto updateDto);

    Page<OrderItemResponseDto> getAllOrderItems(Long orderId, Pageable pageable);

    Page<OrderResponseDto> getAllOrders(Long authenticatedUserId, Pageable pageable);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId);
}
