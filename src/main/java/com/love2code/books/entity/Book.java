package com.love2code.books.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class Book {
	
	private long id;
	
	@Size(min=1, max=30, message="The title length should be between 1 to 30 characters")
	private String title;
	
	@Size(min=1, max=30, message="The author length should be between 1 to 30 characters")
	private String author;
	
	@Size(min=1, max=30, message="The category length should be between 1 to 30 characters")
	private String category;
	
	@Min(value=1, message="The minimum rating should be 1")
	@Max(value=5, message="The maximum rating should be 5")
	private int rating;
	
	public Book(long id, String title, String author, String category, int rating) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.category = category;
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
