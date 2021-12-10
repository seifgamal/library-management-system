package com.library.controller;

import com.library.database.DatabaseManager;
import com.library.model.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CategoryController {

	List<Category> categories;
	private DatabaseManager databaseManager;
	private static final Logger logger = LogManager
			.getLogger(CategoryController.class.getSimpleName());

	public CategoryController(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
		// initially will get all categories from the database and store it in cache
		this.categories = getAllCategoriesFromDB();
		logger.info("initialized successfully");
	}

	public boolean addCategory(Category newCategory) {
		boolean isCategoryAddedToDB = addCategoryToDB(newCategory);
		if (!isCategoryAddedToDB) {
			return false;
		}
		categories.add(newCategory);
		logger.info("A new category is added successfully, category info={}", newCategory.toString());
		return true;
	}

	private boolean addCategoryToDB(Category newCategory) {
		String query = "insert into Category(name, description) values (?, ?)";
		try (PreparedStatement queryStatement = databaseManager.getConnectionObject()
				.prepareStatement(query)) {

			queryStatement.setString(1, newCategory.getName());
			queryStatement.setString(2, newCategory.getDescription());

			int updatedRowsCount = queryStatement.executeUpdate();
			return updatedRowsCount == 1;
		} catch (SQLException e) {
			logger.error("Failed to add category {} to the database, error={}", newCategory.toString(),
					e.getMessage());
		}
		return false;
	}

	public Category getCategoryById(int categoryId) {
		for (Category category : categories) {
			if (category.getId() == categoryId) {
				return category;
			}
		}
		logger.error("Failed to found a category with id={}", categoryId);
		return null;
	}

	public List<Category> getAllcategories() {
		return categories;
	}

	private List<Category> getAllCategoriesFromDB() {
		List<Category> categoriesFromDB = new ArrayList<>();
		String query = "select * from Category";
		try (PreparedStatement queryStatement = databaseManager.getConnectionObject()
				.prepareStatement(query);
				ResultSet resultSet = queryStatement.executeQuery()) {

			while (resultSet.next()) {
				Category Category = new Category();
				Category.setId(resultSet.getInt("id"));
				Category.setName(resultSet.getString("name"));
				Category.setDescription(resultSet.getString("description"));

				categoriesFromDB.add(Category);
			}
		} catch (SQLException e) {
			logger.error("Failed to get categories from database, error={}", e.getMessage());
		}
		return categoriesFromDB;
	}

	public boolean updateCategory(Category updatedCategory) {
		boolean isCategoryUpdatedInDB = updateCategoryInDB(updatedCategory);
		if (isCategoryUpdatedInDB) {
			for (int i = 0; i < categories.size(); i++) {
				if (categories.get(i).getId() == updatedCategory.getId()) {
					logger.info("Category with id={} updated successfully", updatedCategory.getId());
					categories.set(i, updatedCategory);
					return true;
				}
			}
		}
		return false;
	}

	private boolean updateCategoryInDB(Category updatedCategory) {
		String query = "update Category set name = ?, description = ? where id ="
				+ updatedCategory.getId();
		try (PreparedStatement queryStatement = databaseManager.getConnectionObject()
				.prepareStatement(query)) {

			queryStatement.setString(1, updatedCategory.getName());
			queryStatement.setString(2, updatedCategory.getDescription());

			int updatedRowsCount = queryStatement.executeUpdate();
			return updatedRowsCount == 1;
		} catch (SQLException e) {
			logger.error("Failed to update the category in the database, category info={} , error={}",
					updatedCategory.toString(), e.getMessage());
		}
		return false;
	}
}
