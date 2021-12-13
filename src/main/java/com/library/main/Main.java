package com.library.main;

import com.library.controller.BookController;
import com.library.database.*;
import com.library.model.Book;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		/*
		This class currently used for just testing ..
		*/
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		DatabaseManager databaseManager = new DatabaseManager(dbConfig);
		BookController bookController = new BookController(databaseManager);

		Book book = bookController.getBookById(1);
		book.setName("Al Ahly");

		boolean isUpdated = bookController.updateBook(book);

		Book testUpdatedBook= bookController.getBookById(1);
		System.out.println(book);
		bookController.deleteBookById(3);

		/*

		Book newBook = new Book();
		newBook.setName("Seif Book");
		newBook.setDescription("For Seif");
		newBook.setRate(4.9);
		newBook.setAvailable(true);
		newBook.setAuthorId(1);

		bookController.addBook(newBook);


		Book updatedBook = bookController.getBookById(4);
		updatedBook.setName("Abo Treka updated");
		updatedBook.setDescription(updatedBook.getDescription());
		updatedBook.setAuthorId(2);
		bookController.updateBook(updatedBook);
		*/
		/*
		Author newAuthor = new Author();
		newAuthor.setEmail("s@gmail.com");

		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1996, 4, 17);
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		newAuthor.setBirthdate(date);
		newAuthor.setName("Seif Gamal");
		newAuthor.setBio("It's me");
		AuthorController authorController = new AuthorController();
		 */

		List<Book> booksAuthor = bookController.getBooksOfAuthor(2);
		for (Book book: booksAuthor) {
			System.out.println(book);
		}
	}
}
