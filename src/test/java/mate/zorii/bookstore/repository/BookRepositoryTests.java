package mate.zorii.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import mate.zorii.bookstore.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    void findAllByCategoriesId_ExistingCategory_ReturnsBooksList() {
        Long existingCategoryId = 1L;
        int initialBookNumber = 2;
        String expectedBookTitle = "The Hobbit";

        List<Book> actual = bookRepository.findAllByCategories_Id(existingCategoryId);

        assertEquals(initialBookNumber, actual.size());
        assertEquals(expectedBookTitle, actual.get(0).getTitle());
    }
}
