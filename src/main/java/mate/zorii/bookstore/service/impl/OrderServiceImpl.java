package mate.zorii.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.order.CreateOrderRequestDto;
import mate.zorii.bookstore.dto.order.OrderItemResponseDto;
import mate.zorii.bookstore.dto.order.OrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderStatusDto;
import mate.zorii.bookstore.mapper.OrderItemMapper;
import mate.zorii.bookstore.mapper.OrderMapper;
import mate.zorii.bookstore.model.Order;
import mate.zorii.bookstore.model.ShoppingCart;
import mate.zorii.bookstore.repository.OrderItemRepository;
import mate.zorii.bookstore.repository.OrderRepository;
import mate.zorii.bookstore.repository.ShoppingCartRepository;
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
    private final ShoppingCartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(CreateOrderRequestDto createDto,
                                       Long userId) {
        ShoppingCart cart = cartRepository.findByUserId(userId);
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("No items in cart of user with id " + userId);
        }
        Order order = orderMapper.cartToOrder(cart, createDto.shippingAddress());
        orderRepository.save(order);
        orderItemMapper.setOrderItemsFromCart(order, cart);
        cart.clearCart();
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public UpdateOrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusDto updateDto) {
        Order updatedOrder = orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(updateDto.status());
                    return order;
                }).orElseThrow(() -> new EntityNotFoundException("No order with id " + orderId));
        return orderMapper.toUpdateDto(orderRepository.save(updatedOrder));
    }

    @Override
    public Page<OrderItemResponseDto> getAllOrderItems(Long orderId, Pageable pageable) {
        return orderItemRepository.findAllByOrderId(orderId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(Long authenticatedUserId, Pageable pageable) {
        return orderRepository.findAllByUser_Id(authenticatedUserId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        return orderItemRepository.findByIdAndOrder_Id(itemId, orderId)
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No order item by id %d for orderId %d", itemId, orderId)));
    }
}
