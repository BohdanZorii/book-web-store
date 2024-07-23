package mate.zorii.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.order.CreateOrderRequestDto;
import mate.zorii.bookstore.dto.order.OrderItemResponseDto;
import mate.zorii.bookstore.dto.order.OrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderStatusDto;
import mate.zorii.bookstore.mapper.OrderMapper;
import mate.zorii.bookstore.model.Order;
import mate.zorii.bookstore.model.User;
import mate.zorii.bookstore.repository.OrderItemRepository;
import mate.zorii.bookstore.repository.OrderRepository;
import mate.zorii.bookstore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public List<OrderResponseDto> createOrder(CreateOrderRequestDto createDto,
                                              Long authenticatedUserId) {
        Order order = new Order();
        User authenticatedUser = new User();
        authenticatedUser.setId(authenticatedUserId);

        order.setUser(authenticatedUser);
        order.setStatus(Order.Status.PENDING);
        order.setTotal(new BigDecimal(0));
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(createDto.shippingAddress());
        orderRepository.save(order);
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Override
    @Transactional
    public List<OrderResponseDto> updateOrderStatus(Long orderId, UpdateOrderStatusDto updateDto) {
        Order updatedOrder = orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(updateDto.status());
                    return order;
                }).orElseThrow(() -> new EntityNotFoundException("No order with id " + orderId));
        orderRepository.save(updatedOrder);
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Override
    public Page<OrderItemResponseDto> getAllOrderItems(Long orderId, Pageable pageable) {
        return orderItemRepository.findAllByOrderId(orderId, pageable)
                .map(orderMapper::toOrderItemDto);
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(Long authenticatedUserId, Pageable pageable) {
        return orderRepository.findAllByUser_Id(authenticatedUserId, pageable)
                .map(orderMapper::toOrderDto);
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        return orderItemRepository.findByIdAndOrder_Id(itemId, orderId)
                .map(orderMapper::toOrderItemDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No order item by id %d for orderId %d", itemId, orderId)));
    }
}
