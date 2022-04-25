package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.springintro.repository.BookRepository;

import java.io.IOException;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BookRepository bookRepository;


    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BookRepository bookRepository) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //seedData();

        //printAllBooksAfterYear(2000);
        //printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        //printAllAuthorsAndNumberOfTheirBooks();
        //pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        Scanner sc = new Scanner(System.in);
        // Task 1
//        String restriction = sc.nextLine();
//
//        this.bookService.findAllTitlesByAgeRestriction(restriction)
//                        .forEach(System.out::println);

        // Task 2
//        this.bookService.findAllTitlesByEditionAndCopies(EditionType.GOLD, 5000)
//               .forEach(System.out::println);
//

        // Task 3
//        this.bookService.findAllWithPriceNotBetween(5,40)
//                .forEach(b -> System.out.println(b.getTitle() + " - " + b.getPrice()));
//

        // Task 4
//        int releaseYear = Integer.parseInt(sc.nextLine());
//        this.bookService.findBooksNotReleasedIn(releaseYear)
//                .forEach(b -> System.out.println(b.getTitle()));
//

        // Task5
//        String releaseDate = sc.nextLine();
//        this.bookService.findBooksReleasedBefore(releaseDate).forEach(b -> System.out.println(b.getTitle() + " " + b.getEditionType() + " " + b.getPrice()));
//

        // Task 6
//        String endsWith = sc.nextLine();
//        this.authorService.findByFirstNameEndingWith(endsWith)
//                .stream()
//                .map(a -> a.getFirstName() + " " + a.getLastName())
//                .forEach(System.out::println);


        // Task 7
//        String contains = sc.nextLine();
//        this.bookService.findAllTitlesContaining(contains)
//                .stream()
//                .map(b -> b.getTitle())
//                .forEach(System.out::println);


        // Task 8
//        String startsWith = sc.nextLine();
//        this.bookService.findByAuthorLastNameStartsWith(startsWith)
//                .stream()
//                .forEach(b -> System.out.printf("%s (%s %s)%n", b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
//

        // Task 9
//        int number = Integer.parseInt(sc.nextLine());
//        int books = this.bookService.countBooksWithTitleLongerThan(number);
//        System.out.printf("There are %d books with longer title than %d symbols%n", books, number);

        // Task 10
//        this.authorService.getWithTotalCopies()
//                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName() + " - " + a.getTotalCopies()));

        // Task 11
        String givenTitle = sc.nextLine();
        BookSummary bookSummary = this.bookService.getInformationForTitle(givenTitle);
        System.out.printf("%s %s %s %.2f",
                bookSummary.getTitle(), bookSummary.getEditionType(),
                bookSummary.getAgeRestriction(), bookSummary.getPrice());
    }


//    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
//        bookService
//                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
//                .forEach(System.out::println);
//    }
//
//    private void printAllAuthorsAndNumberOfTheirBooks() {
//        authorService
//                .getAllAuthorsOrderByCountOfTheirBooks()
//                .forEach(System.out::println);
//    }
//
//    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
//        bookService
//                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
//                .forEach(System.out::println);
//    }
//
//    private void printAllBooksAfterYear(int year) {
//        bookService
//                .findAllBooksAfterYear(year)
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);
//    }
//
//    private void seedData() throws IOException {
//        categoryService.seedCategories();
//        authorService.seedAuthors();
//        bookService.seedBooks();
//    }

}
