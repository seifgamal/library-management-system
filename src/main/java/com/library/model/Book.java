package com.library.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
@NamedQuery(name = "get_all_books", query = "SELECT b from Book b")
@NamedQuery(name = "get_all_books_by_specific_category", query = "from Book where category_id = :category_id")
public class Book {

	@Id
	@GeneratedValue
	private int id;

	private String name;
	private String description;

	@Column(name = "available")
	private boolean isAvailable;

	private double rate;
	private int authorId;
	private int categoryId;
}
