package mate.zorii.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import mate.zorii.bookstore.dto.book.BookDto;
import mate.zorii.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import mate.zorii.bookstore.dto.book.BookSearchRequestDto;
import mate.zorii.bookstore.exception.EntityNotFoundException;
import mate.zorii.bookstore.mapper.BookMapper;
import mate.zorii.bookstore.model.Book;
import mate.zorii.bookstore.repository.BookRepository;
import mate.zorii.bookstore.repository.SpecificationProvider;
import mate.zorii.bookstore.service.impl.BookServiceImpl;
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
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private SpecificationProvider specificationProvider;
    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("9781234567897");
        book.setPrice(new BigDecimal("29.99"));
        book.setDescription("Test Description");
        book.setCoverImage("test-cover-image.jpg");
        book.setDeleted(false);
        book.setCategories(new HashSet<>());

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setIsbn("9781234567897");
        bookDto.setPrice(new BigDecimal("29.99"));
        bookDto.setDescription("Test Description");
        bookDto.setCoverImage("test-cover-image.jpg");
        bookDto.setCategoryIds(new HashSet<>());
    }

    @Test
    @DisplayName("Save valid book")
    void save_ValidBook_ReturnsBookDto() {
        when(bookMapper.toModel(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.save(bookDto);

        assertEquals(bookDto, actual);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Find all books without pagination")
    void findAll_Unpaged_ReturnsBookDtoList() {
        Page<Book> page = new PageImpl<>(List.of(book));
        when(bookRepository.findAll(Pageable.unpaged())).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        Page<BookDto> actual = bookService.findAll(Pageable.unpaged());

        assertEquals(1, actual.getTotalElements());
        assertEquals(bookDto, actual.getContent().get(0));
    }

    @Test
    @DisplayName("Find book by absent id")
    void findById_AbsentId_ThrowsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> bookService.findById(1L));

        assertEquals("No book found by id 1", ex.getMessage());
    }

    @Test
    @DisplayName("Find book by present id")
    void findById_PresentId_ReturnsBookDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.findById(1L);

        assertEquals(bookDto, actual);
    }

    @Test
    @DisplayName("Update book by absent id")
    void update_AbsentId_ThrowsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class,
                () -> bookService.update(1L, bookDto));

        assertEquals("No book found by id 1", ex.getMessage());
    }

    @Test
    @DisplayName("Update book by present id")
    void update_PresentId_ReturnsBookDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookMapper).updateBookFromDto(book, bookDto);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.update(1L, bookDto);

        assertEquals(bookDto, actual);
        verify(bookMapper, times(1)).updateBookFromDto(book, bookDto);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Search for book by title and author")
    void search_BooksMatchCriteria_ReturnsBooksList() {
        BookSearchRequestDto searchDto = new BookSearchRequestDto("title", "author");
        Specification<Book> spec = mock(Specification.class);
        List<Book> books = List.of(new Book(), new Book());

        when(specificationProvider.getSpecification(searchDto)).thenReturn(spec);
        when(bookRepository.findAll(spec)).thenReturn(books);
        when(bookMapper.toDto(any(Book.class))).thenReturn(new BookDto());

        List<BookDto> actual = bookService.search(searchDto);

        assertNotNull(actual);
        assertEquals(2, actual.size());
        verify(specificationProvider).getSpecification(searchDto);
        verify(bookRepository).findAll(spec);
    }

    @Test
    @DisplayName("Get books by absent category id")
    void getBooksByCategoriesId_AbsentCategoryId_ReturnsEmptyList() {
        when(bookRepository.findAllByCategories_Id(1L)).thenReturn(Collections.emptyList());

        List<BookResponseDtoWithoutCategoryIds> actual = bookService.getBooksByCategoryId(1L);

        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    @DisplayName("Get books by present category id")
    void getBooksByCategoriesId_PresentCategoryId_ReturnsBooksList() {
        BookResponseDtoWithoutCategoryIds dto = new BookResponseDtoWithoutCategoryIds(
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice(),
                book.getDescription(),
                book.getCoverImage()
        );
        when(bookRepository.findAllByCategories_Id(1L)).thenReturn(List.of(book));
        when(bookMapper.toDtoWithoutCategories(book)).thenReturn(dto);

        List<BookResponseDtoWithoutCategoryIds> actual = bookService.getBooksByCategoryId(1L);

        assertEquals(List.of(dto), actual);
    }

    @Test
    @DisplayName("Delete book by present id")
    void deleteById_PresentId_CallsRepositoryDeleteMethod() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}
