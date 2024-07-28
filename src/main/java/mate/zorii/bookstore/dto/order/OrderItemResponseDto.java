package mate.zorii.bookstore.dto.order;

public record OrderItemResponseDto(
        Long id,
        Long bookId,
        int quantity
) {
}
