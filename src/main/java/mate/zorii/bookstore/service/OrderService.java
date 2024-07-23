package mate.zorii.bookstore.service;

import java.awt.print.Pageable;
import java.util.List;
import mate.zorii.bookstore.dto.order.CreateOrderRequestDto;
import mate.zorii.bookstore.dto.order.OrderItemResponseDto;
import mate.zorii.bookstore.dto.order.OrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderStatusDto;
import org.springframework.data.domain.Page;

public interface OrderService {
    List<OrderResponseDto> createOrder(CreateOrderRequestDto requestDto, Long authenticatedUserId);

    List<OrderResponseDto> updateOrderStatus(Long orderId, UpdateOrderStatusDto updateDto);

    Page<OrderItemResponseDto> getAllOrderItems(Long orderId, Pageable pageable);

    Page<OrderResponseDto> getAllOrders(Pageable pageable, Long authenticatedUserId);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId);
}
