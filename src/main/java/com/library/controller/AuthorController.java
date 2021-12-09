package com.library.controller;

import com.library.database.DatabaseManager;
import com.library.model.Author;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorController {

	private List<Author> authors;
	private DatabaseManager databaseManager;
	private static final Logger logger = LogManager.getLogger(AuthorController.class.getSimpleName());

	public AuthorController(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
		// initially will get all authors from the database and store it in cache
		this.authors = getAllAuthorsFromDB();
		logger.info("initialized successfully");
	}

	public boolean addAuthor(Author newAuthor) {
		boolean isAuthorAddedToDB = addAuthorToDB(newAuthor);
		if (!isAuthorAddedToDB) {
			return false;
		}
		authors.add(newAuthor);
		logger.info("A new author is added successfully, author info={}", newAuthor.toString());
		return true;
	}

	private boolean addAuthorToDB(Author newAuthor) {
		String query = "insert into Author(fullName, bio, email, birthday) values (?, ?, ?, ?)";
		try {
			PreparedStatement queryStatement = databaseManager.getConnectionObject()
					.prepareStatement(query);

			queryStatement.setString(1, newAuthor.getFullName());
			queryStatement.setString(2, newAuthor.getBio());
			queryStatement.setDate(3, (Date) newAuthor.getBirthday());

			int updatedRowsCount = queryStatement.executeUpdate();
			queryStatement.close();
			return updatedRowsCount == 1;
		} catch (SQLException e) {
			logger.error("Failed to add author {} to the database, error={}", newAuthor.toString(),
					e.getMessage());
		}
		return false;
	}

	public Author getAuthorById(int authorId) {
		for (Author author : authors) {
			if (author.getId() == authorId) {
				return author;
			}
		}
		logger.error("Failed to found a author with id={}", authorId);
		return null;
	}

	public List<Author> getAllAuthors() {
		return authors;
	}

	private List<Author> getAllAuthorsFromDB() {
		List<Author> authorsFromDB = new ArrayList<>();
		String query = "select * from Author";
		try {
			PreparedStatement queryStatement = databaseManager.getConnectionObject()
					.prepareStatement(query);
			ResultSet resultSet = queryStatement.executeQuery();
			while (resultSet.next()) {
				Author Author = new Author();
				Author.setId(resultSet.getInt("id"));
				Author.setFullName(resultSet.getString("fullName"));
				Author.setBio(resultSet.getString("bio"));
				Author.setBirthday(resultSet.getDate("birthday"));
				Author.setEmail(resultSet.getString("email"));

				authorsFromDB.add(Author);
			}
			queryStatement.close();
			resultSet.close();
		} catch (SQLException e) {
			logger.error("Failed to get authors from database, error={}", e.getMessage());
		}
		return authorsFromDB;
	}

	public boolean updateAuthor(Author updatedAuthor) {
		boolean isAuthorUpdatedInDB = updateAuthorInDB(updatedAuthor);
		if (isAuthorUpdatedInDB) {
			for (int i = 0; i < authors.size(); i++) {
				if (authors.get(i).getId() == updatedAuthor.getId()) {
					logger.info("Author with id={} updated successfully", updatedAuthor.getId());
					authors.set(i, updatedAuthor);
					return true;
				}
			}
		}
		return false;
	}

	private boolean updateAuthorInDB(Author updatedAuthor) {
		String query =
				"update Author set fullName = ?, bio = ?, email = ?, " + "birthday = ? where id ="
						+ updatedAuthor.getId();
		try {
			PreparedStatement queryStatement = databaseManager.getConnectionObject()
					.prepareStatement(query);

			queryStatement.setString(1, updatedAuthor.getFullName());
			queryStatement.setString(2, updatedAuthor.getBio());
			queryStatement.setString(3, updatedAuthor.getEmail());
			queryStatement.setDate(4, (Date) updatedAuthor.getBirthday());

			int updatedRowsCount = queryStatement.executeUpdate();
			queryStatement.close();
			return updatedRowsCount == 1;
		} catch (SQLException e) {
			logger.error("Failed to update the author in the database, author info={}, error={}",
					updatedAuthor.toString(), e.getMessage());
		}
		return false;
	}

	public boolean deleteAuthorById(int authorId) {
		Author targetAuthor = getAuthorById(authorId);
		if (targetAuthor == null) {
			logger.error("Failed to delete a author with id={}", authorId);
			return false;
		}
		boolean isAuthorRemovedFromDB = deleteAuthorByIdFromDB(authorId);
		if (isAuthorRemovedFromDB) {
			authors.remove(targetAuthor);
			logger.info("Author with id={} deleted successfully", authorId);
			return true;
		}
		return false;
	}

	private boolean deleteAuthorByIdFromDB(int authorId) {
		String query = "delete from Author where id = " + authorId;
		try {
			PreparedStatement queryStatement = databaseManager.getConnectionObject()
					.prepareStatement(query);
			int updatedRowsCount = queryStatement.executeUpdate();
			queryStatement.close();
			return updatedRowsCount == 1;
		} catch (SQLException e) {
			logger.error("Failed to remove book with id={} from database, error={}", authorId,
					e.getMessage());
		}
		return false;
	}
}
