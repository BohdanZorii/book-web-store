package mate.zorii.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.order.CreateOrderRequestDto;
import mate.zorii.bookstore.dto.order.OrderItemResponseDto;
import mate.zorii.bookstore.dto.order.OrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderResponseDto;
import mate.zorii.bookstore.dto.order.UpdateOrderStatusDto;
import mate.zorii.bookstore.service.AuthenticationService;
import mate.zorii.bookstore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@Validated
@RequiredArgsConstructor
@Tag(name = "Order API", description = "APIs for managing orders")
public class OrderController {
    private final OrderService orderService;
    private final AuthenticationService authService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create a new order",
            description = "Creates a new order for the currently authenticated user.")
    public OrderResponseDto placeOrder(@RequestBody @Valid CreateOrderRequestDto requestDto,
                                       Authentication auth) {
        return orderService.placeOrder(requestDto, authService.getAuthenticatedUserId(auth));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all orders for authenticated user",
            description = "Retrieves all orders for authenticated user with pagination support.")
    public Page<OrderResponseDto> getAllOrders(Pageable pageable, Authentication auth) {
        return orderService.getAllOrders(authService.getAuthenticatedUserId(auth), pageable);
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status",
            description = "Updates the status of a specific order.")
    public UpdateOrderResponseDto updateOrderStatus(
            @PathVariable @Positive Long orderId,
            @RequestBody @Valid UpdateOrderStatusDto updateOrderStatusDto) {
        return orderService.updateOrderStatus(orderId, updateOrderStatusDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all items in an order",
            description = "Retrieves all items in a specific order with pagination support.")
    public Page<OrderItemResponseDto> getAllOrderItems(@PathVariable @Positive Long orderId,
                                                       Pageable pageable) {
        return orderService.getAllOrderItems(orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get a specific item in an order",
            description = "Retrieves a specific item from a specific order.")
    public OrderItemResponseDto getOrderItem(@PathVariable @Positive Long orderId,
                                             @PathVariable @Positive Long itemId) {
        return orderService.getOrderItem(orderId, itemId);
    }
}
