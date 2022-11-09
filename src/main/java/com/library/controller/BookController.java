package com.library.controller;

import com.library.exception.Book.BookNotFoundException;
import com.library.model.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(value = "category", required = false) Integer categoryId,
            @RequestParam(value = "author", required = false) Integer authorId) {
        List<Book> books = bookService.getAllBooks();

        // filter by category
        if (categoryId != null) {
            books = bookService.filterListOfBooksByCategory(books, categoryId);
        }
        // filter by author
        if (authorId != null) {
            books = bookService.filterListOfBooksByAuthor(books, authorId);
        }

        return books;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) throws BookNotFoundException {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book updatedBook, @PathVariable Long id) throws BookNotFoundException {
        return bookService.updateBook(updatedBook, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }
}