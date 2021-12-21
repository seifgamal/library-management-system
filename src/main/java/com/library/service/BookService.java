package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Book addBook(Book newBook) {
		return bookRepository.add(newBook);
	}

	public List<Book> getAllBooks() {
		return bookRepository.getAll();
	}

	public List<Book> filterListOfBooksByCategory(List<Book> books, Integer categoryId) {
		return books.stream().filter(b -> b.getCategoryId() == categoryId).collect(Collectors.toList());
	}

	public List<Book> filterListOfBooksByAuthor(List<Book> books, Integer authorId) {
		return books.stream().filter(b -> b.getAuthorId() == authorId).collect(Collectors.toList());
	}

	public Book getBookById(int id) {
		return bookRepository.getById(id);
	}

	public Book updateBook(Book updatedBook, int id) {
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

	public void deleteBookById(int id) {
		bookRepository.deleteById(id);
	}
}
