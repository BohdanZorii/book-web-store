package mate.zorii.bookstore.dto.book;

import java.math.BigDecimal;

public record BookResponseDtoWithoutCategoryIds(
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage
) {
}
