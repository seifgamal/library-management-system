package com.library.model;


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
public class Book {
	private int id;
	private String name;
	private String description;
	private boolean isAvailable;
	private double rate;
	private int authorId;
	private int categoryId;
}
