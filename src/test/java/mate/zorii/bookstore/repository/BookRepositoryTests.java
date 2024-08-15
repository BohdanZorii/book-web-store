package mate.zorii.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import mate.zorii.bookstore.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTests {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find all books by not existing category id")
    void findAllByCategoriesId_NotExistingCategory_ReturnsEmptyList() {
        Long notExistingCategoryId = 3L;

        List<Book> actual = bookRepository.findAllByCategories_Id(notExistingCategoryId);

        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Find all books by existing category id")
    @Sql(scripts = "/database/insert-book-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/database/delete-book-by-id-1.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoriesId_ExistingCategory_ReturnsBooksList() {
        Long existingCategoryId = 1L;
        int initialBooksNumber = 1;
        String expectedBookTitle = "The Hobbit";

        List<Book> actual = bookRepository.findAllByCategories_Id(existingCategoryId);

        assertEquals(initialBooksNumber, actual.size());
        assertEquals(expectedBookTitle, actual.get(0).getTitle());
    }
}
