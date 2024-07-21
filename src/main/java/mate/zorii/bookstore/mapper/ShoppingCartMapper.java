package mate.zorii.bookstore.mapper;

import mate.zorii.bookstore.config.MapperConfig;
import mate.zorii.bookstore.dto.shoppingcart.CartItemDto;
import mate.zorii.bookstore.dto.shoppingcart.CartItemUpdateDto;
import mate.zorii.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mate.zorii.bookstore.model.CartItem;
import mate.zorii.bookstore.model.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "bookId", source = "book.id")
    CartItemDto toCartItemDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookById")
    @Mapping(target = "id", ignore = true)
    CartItem toModel(CartItemDto cartItemDto, @Context ShoppingCart shoppingCart);

    @AfterMapping
    default void setShoppingCart(@MappingTarget CartItem cartItem,
                                 @Context ShoppingCart shoppingCart) {
        cartItem.setShoppingCart(shoppingCart);
    }

    void updateCartItemFromDto(@MappingTarget CartItem cartItem, CartItemUpdateDto dto);

    @Mapping(target = "userId", source = "user.id")
    ShoppingCartResponseDto toShoppingCartDto(ShoppingCart shoppingCart);
}
