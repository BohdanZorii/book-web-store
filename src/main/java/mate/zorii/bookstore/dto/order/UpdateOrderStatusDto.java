package mate.zorii.bookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import mate.zorii.bookstore.model.Order;
import mate.zorii.bookstore.validation.ValidEnum;

public record UpdateOrderStatusDto(
        @NotNull
        @ValidEnum(enumClass = Order.Status.class)
        Order.Status status
) {
}
