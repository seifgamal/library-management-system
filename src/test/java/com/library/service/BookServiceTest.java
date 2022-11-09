package com.library.service;

import com.library.exception.Book.BookNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@Transactional
class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    void addBook_success() {
        String bName = "Book-RAND";
        Book newBook = new Book(null, bName, "HERE'S A BOOK", true, 0, 1, 1);

        Book addedBook = bookService.addBook(newBook);
        then(addedBook.getName()).isEqualTo(newBook.getName());

        Optional<Book> retrievedBook = bookRepository.findById(newBook.getId());
        then(retrievedBook).isNotNull();
    }

    @Test
    void getBookById_success() throws BookNotFoundException {
        Book savedBook = bookRepository.save(new Book(null, "BOOK2", "HERE'S A BOOK", true, 0, 1, 1));
        Book retrievedBook = bookService.getBookById(savedBook.getId());

        then(retrievedBook.getName()).isEqualTo(savedBook.getName());
    }

    @Test
    void getBookById_missingBook_BookNotFoundThrown() {
        Long id = 9232L; // Random id
        Throwable throwable = catchThrowable(() -> this.bookService.getBookById(id));
        then(throwable).isInstanceOf(BookNotFoundException.class);
    }
}