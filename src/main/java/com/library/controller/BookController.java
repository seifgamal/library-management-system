package com.library.controller;

import com.library.database.DatabaseManager;
import com.library.model.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookController {

	private List<Book> books;
	private DatabaseManager databaseManager;
	private static final Logger logger = LogManager.getLogger(BookController.class.getSimpleName());

	public BookController(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
		// initially will get all books from the database and store it in cache
		this.books = this.getAllBooksFromDB();
		logger.debug("initialized successfully");
	}

	public boolean addBook(Book newBook) {
		boolean isBookAddedToDB = addBookToDB(newBook);
		if (!isBookAddedToDB) {
			return false;
		}
		books.add(newBook);
		logger.info("A new book is added successfully, book info={}", newBook.toString());
		return true;
	}

	private boolean addBookToDB(Book newBook) {
		String query = "insert into Book(name, description, available, rate, authorId, categoryId) values (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement queryStatement = databaseManager.getConnectionObject()
				.prepareStatement(query)) {

			queryStatement.setString(1, newBook.getName());
			queryStatement.setString(2, newBook.getDescription());
			queryStatement.setBoolean(3, newBook.isAvailable());
			queryStatement.setDouble(4, newBook.getRate());
			queryStatement.setInt(5, newBook.getAuthorId());
			queryStatement.setInt(6, newBook.getCategoryId());

			int updatedRowsCount = queryStatement.executeUpdate();
			return updatedRowsCount == 1;
		} catch (SQLException e) {
			logger.error("Failed to add book {} to the database, error={}", newBook.toString(),
					e.getMessage());
		}
		return false;
	}

	public Book getBookById(int bookId) {
		for (Book book : books) {
			if (book.getId() == bookId) {
				return book;
			}
		}
		logger.error("Failed to found a book with id= {}", bookId);
		return null;
	}

	public List<Book> getAllBooks() {
		return books;
	}

	public List<Book> getAllBooksSortedByName() {
		// WARNING: this line modifying the original list
		books.sort(Comparator.comparing(Book::getName));
		return books;
	}

	public List<Book> getBooksOfAuthor(int authorId) {
		return books.stream().filter(b -> authorId == b.getAuthorId()).collect(Collectors.toList());
	}

	private List<Book> getAllBooksFromDB() {
		List<Book> booksFromDB = new ArrayList<>();
		String query = "select * from Book";
		try (PreparedStatement queryStatement = databaseManager.getConnectionObject()
				.prepareStatement(query);
				ResultSet resultSet = queryStatement.executeQuery()) {

			while (resultSet.next()) {
				Book book = new Book();
				book.setId(resultSet.getInt("id"));
				book.setName(resultSet.getString("name"));
				book.setDescription(resultSet.getString("description"));
				book.setAvailable(resultSet.getBoolean("available"));
				book.setRate(resultSet.getDouble("rate"));
				book.setAuthorId(resultSet.getInt("authorId"));

				booksFromDB.add(book);
			}
		} catch (SQLException e) {
			logger.error("Failed to get books from database, error={}", e.getMessage());
		}
		return booksFromDB;
	}

	public boolean updateBook(Book updatedBook) {
		boolean isBookUpdatedInDB = updateBookInDB(updatedBook);
		if (isBookUpdatedInDB) {
			for (int i = 0; i < books.size(); i++) {
				if (books.get(i).getId() == updatedBook.getId()) {
					logger.info("Book with id={} updated successfully", updatedBook.getId());
					books.set(i, updatedBook);
					return true;
				}
			}
		}
		return false;
	}

	private boolean updateBookInDB(Book updatedBook) {
		String query =
				"update Book set name = ?, description = ?, available = ?, " + "rate = ? where id ="
						+ updatedBook.getId();
		try (PreparedStatement queryStatement = databaseManager.getConnectionObject()
				.prepareStatement(query)) {

			queryStatement.setString(1, updatedBook.getName());
			queryStatement.setString(2, updatedBook.getDescription());
			queryStatement.setBoolean(3, updatedBook.isAvailable());
			queryStatement.setDouble(4, updatedBook.getRate());

			int updatedRowsCount = queryStatement.executeUpdate();
			return updatedRowsCount == 1;
		} catch (SQLException e) {
			logger.error("Failed to update the book in the database, book info={}, error={}",
					updatedBook.toString(), e.getMessage());
		}
		return false;
	}

	public boolean deleteBookById(int bookId) {
		Book targetBook = getBookById(bookId);
		if (targetBook == null) {
			logger.error("Failed to delete a book with id={}", bookId);
			return false;
		}
		boolean isBookRemovedFromDB = deleteBookByIdFromDB(bookId);
		if (isBookRemovedFromDB) {
			books.remove(targetBook);
			logger.info("Book with id={} deleted successfully", bookId);
			return true;
		}
		return false;
	}

	private boolean deleteBookByIdFromDB(int bookId) {
		String query = "delete from Book where id = " + bookId;
		try (PreparedStatement queryStatement = databaseManager.getConnectionObject()
				.prepareStatement(query)) {

			int updatedRowsCount = queryStatement.executeUpdate();
			return updatedRowsCount == 1;
		} catch (SQLException e) {
			logger.error("Failed to remove book with id={} from database, error={}", bookId,
					e.getMessage());
		}
		return false;
	}
}
