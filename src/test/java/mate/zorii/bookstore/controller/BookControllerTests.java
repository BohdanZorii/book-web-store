package mate.zorii.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import mate.zorii.bookstore.dto.book.BookDto;
import mate.zorii.bookstore.dto.book.BookSearchRequestDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTests {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all books with pagination")
    @Sql(scripts = "classpath:database/insert-3-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-all-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAll_ReturnsPageOfBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/books")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);
        JsonNode contentNode = jsonNode.path("content");
        List<BookDto> books = objectMapper.readValue(contentNode.toString(),
                new TypeReference<>() {});

        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(3, books.size());
        assertEquals(0, jsonNode.path("number").asInt());
        assertEquals(10, jsonNode.path("size").asInt());
        assertEquals(3, jsonNode.path("totalElements").asLong());
        assertTrue(jsonNode.path("first").asBoolean());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get book by existing ID")
    @Sql(scripts = "classpath:database/insert-book-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-all-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById_ExistingId_ReturnsBook() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);
        assertNotNull(actual);
        assertEquals(1L, actual.getId());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Search books with criteria")
    @Sql(scripts = "classpath:database/insert-3-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-all-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void search_BooksByCriteria_ReturnsBooksList() throws Exception {
        String titlePart = "the";
        BookSearchRequestDto searchRequest = new BookSearchRequestDto(titlePart, null);
        String jsonRequest = objectMapper.writeValueAsString(searchRequest);

        MvcResult result = mockMvc.perform(get("/books/search")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(book ->
                book.getTitle().toLowerCase().contains(titlePart)));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a new valid book")
    @Sql(scripts = "classpath:database/delete-all-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_ValidBookDto_ReturnsIsOkStatus() throws Exception {
        BookDto bookDto = new BookDto()
                .setId(1L)
                .setTitle("LOTR")
                .setAuthor("Tolkien")
                .setIsbn("0321750123")
                .setPrice(BigDecimal.valueOf(149.99));
        String jsonRequest = objectMapper.writeValueAsString(bookDto);

        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(bookDto, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create book with invalid ISBN")
    void create_InvalidIsbn_ReturnsBadRequestStatus() throws Exception {
        BookDto bookDto = new BookDto()
                .setId(1L)
                .setTitle("LOTR")
                .setAuthor("Tolkien")
                .setIsbn("122-123-1331-13-313-13")
                .setPrice(BigDecimal.valueOf(149.99));
        String jsonRequest = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update book by ID")
    @Sql(scripts = "classpath:database/insert-book-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-all-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_ExistingBookDto_ReturnsUpdatedBook() throws Exception {
        BookDto updatedBookDto = new BookDto()
                .setTitle("LOTR Updated")
                .setAuthor("Tolkien Updated")
                .setIsbn("0321750124")
                .setPrice(BigDecimal.valueOf(159.99));
        String jsonRequest = objectMapper.writeValueAsString(updatedBookDto);

        MvcResult result = mockMvc.perform(put("/books/{id}", 1L)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);
        assertNotNull(actual);
        assertEquals("LOTR Updated", actual.getTitle());
        assertEquals("Tolkien Updated", actual.getAuthor());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete book by ID")
    @Sql(scripts = "classpath:database/insert-book-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-all-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete_ExistingBookId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
