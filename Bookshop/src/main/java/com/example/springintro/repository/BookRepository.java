package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    //List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    @Query("SELECT b.title FROM Book b WHERE  b.ageRestriction = :ageRestriction")
    List<String> findByAgeRestriction(@Param("ageRestriction") AgeRestriction restriction);

    List<Book> findByEditionTypeAndCopiesLessThan(EditionType type, int copies);

    List<Book> findAllBooksByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);

    List<Book> findByReleaseDateBeforeOrReleaseDateAfter(LocalDate dateBefore, LocalDate dateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate dateBefore);

    List<Book> findByTitleContaining(String contains);

    List<Book> findByAuthorLastNameStartingWith(String startsWith);

    @Query("SELECT COUNT(b) FROM Book b WHERE length(b.title) > :number")
    int countBooksWithTitleLongerThan(int number);

    @Query("""
        SELECT b.title AS title, b.editionType AS editionType, 
        b.ageRestriction AS ageRestriction, b.price AS price
        FROM Book b
        WHERE b.title = :givenTitle
            """)
     BookSummary findSummaryForTitle(String givenTitle);
}
