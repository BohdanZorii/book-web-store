package mate.zorii.bookstore.service;

import java.util.List;
import mate.zorii.bookstore.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
