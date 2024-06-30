package mate.zorii.bookstore.repository;

import jakarta.persistence.criteria.Predicate;
import mate.zorii.bookstore.dto.BookSearchRequestDto;
import mate.zorii.bookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationProvider {
    public Specification<Book> getSpecification(BookSearchRequestDto searchRequestDto) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (searchRequestDto.title() != null && !searchRequestDto.title().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + searchRequestDto.title().toLowerCase() + "%"
                        )
                );
            }
            if (searchRequestDto.author() != null && !searchRequestDto.author().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("author")),
                                "%" + searchRequestDto.author().toLowerCase() + "%"
                        )
                );
            }
            return predicate;
        };
    }
}
