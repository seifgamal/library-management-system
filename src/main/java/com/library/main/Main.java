package com.library.main;

import com.library.controller.BookController;
import com.library.database.*;
import com.library.model.Book;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		/*
		This class currently used for just testing ..

		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		DatabaseManager databaseManager = new DatabaseManager(dbConfig);
		BookController bookController = new BookController(databaseManager);

		List<Book> booksAuthor = bookController.getBooksOfAuthor(2);
		for (Book book: booksAuthor) {
			System.out.println(book);
		}
		*/
	}
}
