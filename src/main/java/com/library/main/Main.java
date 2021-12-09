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

		List<Book> booksAuthor = bookController.getBooksOfAuthor(2);
		for (Book book: booksAuthor) {
			System.out.println(book);
		}
	}
}
