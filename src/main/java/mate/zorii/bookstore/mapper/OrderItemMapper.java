package mate.zorii.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mate.zorii.bookstore.config.MapperConfig;
import mate.zorii.bookstore.dto.order.OrderItemResponseDto;
import mate.zorii.bookstore.model.CartItem;
import mate.zorii.bookstore.model.Order;
import mate.zorii.bookstore.model.OrderItem;
import mate.zorii.bookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "book.price")
    OrderItem fromCartItem(CartItem cartItem);

    default void setOrderItemsFromCart(Order order, ShoppingCart cart) {
        Set<OrderItem> orderItems = cart.getCartItems().stream()
                .map(this::fromCartItem)
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        orderItems.forEach(oi -> oi.setOrder(order));
    }
}
