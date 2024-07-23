package mate.zorii.bookstore.service.impl;

import java.awt.print.Pageable;
import java.util.List;
import mate.zorii.bookstore.dto.order.CreateOrderRequestDto;
import mate.zorii.bookstore.dto.order.OrderItemResponseDto;
import mate.zorii.bookstore.dto.order.OrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderStatusDto;
import mate.zorii.bookstore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public List<OrderResponseDto> createOrder(CreateOrderRequestDto requestDto,
                                              Long authenticatedUserId) {
        return null;
    }

    @Override
    public List<OrderResponseDto> updateOrderStatus(Long orderId, UpdateOrderStatusDto updateDto) {
        return null;
    }

    @Override
    public Page<OrderItemResponseDto> getAllOrderItems(Long orderId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(Pageable pageable, Long authenticatedUserId) {
        return null;
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        return null;
    }
}
