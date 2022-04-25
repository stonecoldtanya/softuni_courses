package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    //List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllTitlesByEditionAndCopies(EditionType type, int copies);

    List<Book> findAllWithPriceNotBetween(float lowerBound, float upperBound);

    List<Book> findBooksNotReleasedIn(int releaseYear);

    List<Book> findBooksReleasedBefore(String date);

    List<Book> findAllTitlesContaining(String contains);

    List<Book> findByAuthorLastNameStartsWith(String startsWith);

    int countBooksWithTitleLongerThan(int number);

    BookSummary getInformationForTitle(String givenTitle);
}
