package mate.zorii.bookstore.mapper;

import mate.zorii.bookstore.config.MapperConfig;
import mate.zorii.bookstore.dto.order.OrderItemResponseDto;
import mate.zorii.bookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toOrderItemDto(OrderItem orderItem);
}
