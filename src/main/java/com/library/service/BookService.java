package com.library.service;

import com.library.exception.Book.BookNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> filterListOfBooksByCategory(@NotNull List<Book> books, Integer categoryId) {
        return books.stream().filter(b -> b.getCategoryId() == categoryId).collect(Collectors.toList());
    }

    public List<Book> filterListOfBooksByAuthor(@NotNull List<Book> books, Integer authorId) {
        return books.stream().filter(b -> b.getAuthorId() == authorId).collect(Collectors.toList());
    }

    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException());
    }

    public Book updateBook(Book updatedBook, Long id) throws BookNotFoundException {
        Book oldBook = getBookById(id);
        if (oldBook == null) {
            return null;
        }

        oldBook.setName(updatedBook.getName());
        oldBook.setDescription(updatedBook.getDescription());
        oldBook.setAvailable(updatedBook.isAvailable());

        addBook(oldBook);
        return oldBook;
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
