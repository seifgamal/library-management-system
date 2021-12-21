package com.library.repository;

import com.library.model.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@NoArgsConstructor
public class BookRepository {

	@PersistenceContext
	EntityManager entityManager;

	public Book add(Book book) {
		return entityManager.merge(book);
	}

	public Book update(Book book) {
		return entityManager.merge(book);
	}

	public List<Book> getAll() {
		TypedQuery<Book> getAll = entityManager.createNamedQuery("get_all_books", Book.class);
		return getAll.getResultList();
	}

	public Book getById(int bookId) {
		return entityManager.find(Book.class, bookId);
	}

	public void deleteById(int bookId) {
		Book book = entityManager.find(Book.class, bookId);
		entityManager.remove(book);
	}
}
