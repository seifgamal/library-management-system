package com.library.controller;

import com.library.model.Book;

import com.library.service.BookService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public Book getBookById(@PathVariable int id) {
		return bookService.getBookById(id);
	}

	@PutMapping("/{id}")
	public Book updateBook(@RequestBody Book updatedBook, @PathVariable int id) {
		return bookService.updateBook(updatedBook, id);
	}

	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable int id) {
		bookService.deleteBookById(id);
	}
}
