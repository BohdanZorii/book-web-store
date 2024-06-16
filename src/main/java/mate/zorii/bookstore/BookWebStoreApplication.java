package mate.zorii.bookstore;

import java.math.BigDecimal;
import mate.zorii.bookstore.model.Book;
import mate.zorii.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookWebStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookWebStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setTitle("Spring tutorial");
                book.setAuthor("Me");
                book.setIsbn("idk what it is");
                book.setPrice(BigDecimal.TEN);
                bookService.save(book);
                System.out.println(bookService.findAll());
            }
        };
    }
}
