package mate.zorii.bookstore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookDto {
    private Long id;

    @NotBlank
    @Size(max = 255, message = "Title must be less than 256 characters")
    private String title;

    @NotBlank
    @Size(max = 255, message = "Author must be less than 256 characters")
    private String author;

    @NotBlank
    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "Invalid ISBN format")
    private String isbn;

    @NotNull
    @Positive
    private BigDecimal price;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    @Size(max = 255, message = "Cover image URL must be less than 256 characters")
    private String coverImage;

    private Set<Long> categoryIds;
}
