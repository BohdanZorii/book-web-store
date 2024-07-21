package mate.zorii.bookstore.dto.shoppingcart;

public record CartItemResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity
) {
}
