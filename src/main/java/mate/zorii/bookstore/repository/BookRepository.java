package mate.zorii.bookstore.repository;

import java.util.List;
import mate.zorii.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
