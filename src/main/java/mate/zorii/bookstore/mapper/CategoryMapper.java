package mate.zorii.bookstore.mapper;

import mate.zorii.bookstore.config.MapperConfig;
import mate.zorii.bookstore.dto.category.CategoryDto;
import mate.zorii.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryDto dto);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromDto(@MappingTarget Category category, CategoryDto dto);
}
