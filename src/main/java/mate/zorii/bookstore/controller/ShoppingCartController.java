package mate.zorii.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.zorii.bookstore.dto.shoppingcart.CartItemDto;
import mate.zorii.bookstore.dto.shoppingcart.CartItemUpdateDto;
import mate.zorii.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mate.zorii.bookstore.service.AuthenticationService;
import mate.zorii.bookstore.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@Validated
@RequiredArgsConstructor
@Tag(name = "Shopping Cart API", description = "APIs for managing the shopping cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final AuthenticationService authService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get shopping cart for authenticated user",
            description = "Retrieves the shopping cart for the authenticated user.")
    public ShoppingCartResponseDto findByUser(Authentication auth) {
        return shoppingCartService.findByUserId(authService.getAuthenticatedUserId(auth));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add item to shopping cart",
            description = "Adds a new item to the shopping cart for the authenticated user.")
    public ShoppingCartResponseDto addCartItem(
            @RequestBody @Valid CartItemDto cartItemDto,
            Authentication auth) {
        return shoppingCartService.addCartItem(cartItemDto,
                authService.getAuthenticatedUserId(auth));
    }

    @PutMapping("items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update item quantity in shopping cart",
            description = "Updates the quantity of a specific item in the shopping cart.")
    public ShoppingCartResponseDto updateBookQuantity(
            @PathVariable @Positive Long cartItemId,
            @RequestBody @Valid CartItemUpdateDto cartItemUpdateDto,
            Authentication auth) {
        return shoppingCartService.updateCartItem(
                cartItemId,
                authService.getAuthenticatedUserId(auth),
                cartItemUpdateDto);
    }

    @DeleteMapping("items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete item from shopping cart",
            description = "Removes a specific item from the shopping cart.")
    public void deleteCartItem(@PathVariable @Positive Long cartItemId,
                               Authentication auth) {
        shoppingCartService.deleteCartItem(cartItemId, authService.getAuthenticatedUserId(auth));
    }
}
