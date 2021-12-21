package com.library;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Test implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	BookRepository bookRepository;
	public static void main(String[] args) {
		SpringApplication.run(Test.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//insert Books
		Book newBook = new Book();
		newBook.setName("JPA");
		newBook.setAuthorId(1);
		newBook.setCategoryId(1);
		newBook.setAvailable(true);
		newBook.setDescription("JPA tutorial works");
		newBook.setRate(5);
		/*
		logger.info("\n\n>> Inserting Book: {}\n", bookRepository.add(newBook));

		Book newBookUpdate = newBook;
		newBookUpdate.setName("JPA update");
		//update Book
		logger.info("\n\n>> Updating Book with Id 3: {}\n", bookRepository.update(newBookUpdate));
		//get Book
		//logger.info("\n\n>> Book with id 3: {}\n", repo.getById(newBookUpdate.getId()));
		//delete Book
		//repo.deleteById(2);
		//get all Books
		logger.info("\n\n>> All Books Data: {}", bookRepository.getAllBooks());
		*/
	}
}