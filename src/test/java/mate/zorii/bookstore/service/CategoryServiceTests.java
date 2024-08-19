package mate.zorii.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import mate.zorii.bookstore.dto.category.CategoryDto;
import mate.zorii.bookstore.exception.EntityNotFoundException;
import mate.zorii.bookstore.mapper.CategoryMapper;
import mate.zorii.bookstore.model.Category;
import mate.zorii.bookstore.repository.CategoryRepository;
import mate.zorii.bookstore.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Category name");
        category.setDescription("Category description");
        categoryDto = new CategoryDto(1L, "Category name", "Category description");
    }

    @Test
    @DisplayName("Save valid category")
    void save_ValidCategory_ReturnsCategoryDto() {
        when(categoryMapper.toModel(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.save(categoryDto);

        assertEquals(categoryDto, actual);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Find all categories without pagination")
    void findAll_Unpaged_ReturnsCategoryDtoList() {
        Page<Category> page = new PageImpl<>(List.of(category));
        when(categoryRepository.findAll(Pageable.unpaged())).thenReturn(page);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        Page<CategoryDto> actual = categoryService.findAll(Pageable.unpaged());

        assertEquals(1, actual.getTotalElements());
        assertEquals(categoryDto, actual.getContent().get(0));
    }

    @Test
    @DisplayName("Find category by absent id")
    void findById_AbsentId_ThrowsException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class,
                () -> categoryService.findById(1L));

        assertEquals("No category found by id 1", ex.getMessage());
    }

    @Test
    @DisplayName("Find category by present id")
    void findById_PresentId_ReturnsCategoryDto() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.findById(1L);

        assertEquals(categoryDto, actual);
    }

    @Test
    @DisplayName("Update category by absent id")
    void update_AbsentId_ThrowsException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(1L, categoryDto));

        assertEquals("No category found by id 1", ex.getMessage());
    }

    @Test
    @DisplayName("Update category by present id")
    void update_PresentId_ReturnsCategoryDto() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryMapper).updateCategoryFromDto(category, categoryDto);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.update(1L, categoryDto);

        assertEquals(categoryDto, actual);
        verify(categoryMapper, times(1)).updateCategoryFromDto(category, categoryDto);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Delete category by present id")
    void deleteById_PresentId_CallsRepositoryDeleteMethod() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteById(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
