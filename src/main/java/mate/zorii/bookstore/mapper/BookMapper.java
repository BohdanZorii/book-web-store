package mate.zorii.bookstore.mapper;

import java.util.stream.Collectors;
import mate.zorii.bookstore.config.MapperConfig;
import mate.zorii.bookstore.dto.book.BookDto;
import mate.zorii.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import mate.zorii.bookstore.model.Book;
import mate.zorii.bookstore.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            bookDto.setCategoryIds(book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }

    BookResponseDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(BookDto bookDto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, BookDto bookDto) {
        if (bookDto.getCategoryIds() != null) {
            book.setCategories(bookDto.getCategoryIds().stream()
                    .map(categoryId -> {
                        Category category = new Category();
                        category.setId(categoryId);
                        return category;
                    }).collect(Collectors.toSet()));
        }
    }

    @Mapping(target = "id", ignore = true)
    void updateBookFromDto(@MappingTarget Book book, BookDto bookDto);
}
