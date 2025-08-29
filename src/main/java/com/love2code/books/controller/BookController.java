package com.love2code.books.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.love2code.books.entity.Book;
import com.love2code.books.exception.BookNotFoundException;
import com.love2code.books.request.BookRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Tag(name = "Books Rest API Endpoints", description = "Operations related to books")
@RestController
@RequestMapping("/api/books")
public class BookController {
	
	public BookController() {
		initializeBooks();
	}

	private List<Book> books = new ArrayList<>();
	
	private void initializeBooks() {
		books.addAll(List.of(
				new Book(1, "Fundamentals of Computer Programming", "Anitta Sunny", "Computer Science", 5), 
				new Book(2, "Math in real life", "Praveen Jose", "Math", 5), 
				new Book(3, "Secrets of happy life", "Anmary Sunny", "Science", 5), 
				new Book(4, "The Will Power", "Leena Sunny", "Science", 5), 
				new Book(5, "Why we can't", "Anila Sunny", "Sciece", 5),
				new Book(6, "The Pirate's treasure", "Sunny cv", "History", 5)));
	}
	
	private Book convertToBook(long id, BookRequest bookRequest) {
		return new Book(id, bookRequest.getTitle(), bookRequest.getAuthor(), 
				bookRequest.getCategory(), bookRequest.getRating());
	}
	
	@Operation(summary = "Get all books", description = "Retrieve all the books from list")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Book> getBooks(@Parameter(description = "Optional query parameter") @RequestParam (required = false) String category) {
		if(category == null) return books;
		
		return books.stream().filter(book -> book.getCategory().equalsIgnoreCase(category)).toList();
		
	}
	
	@Operation(summary = "Get book by ID", description = "Retrieve a specific book with the given id")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable @Min(value = 1) long id) {
		return books.stream()
			 .filter(book -> book.getId() == id).findFirst().orElseThrow(() ->  new BookNotFoundException("Book not found - " + id));
	}
	
	@Operation(summary = "Create book", description = "Add a new book to the list")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void createBook(@RequestBody @Valid BookRequest newBookRequest) {
		Boolean isNewBook = books.stream().noneMatch(book -> book.getTitle().equalsIgnoreCase(newBookRequest.getTitle()));
		if(isNewBook) {
			long id = books.isEmpty() ? 1 : books.get(books.size() - 1).getId() + 1;
			Book book = convertToBook(id, newBookRequest);
			books.add(book);
		}
	}
	
	@Operation(summary = "Update book", description = "Modify details of an existing book in the book list")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	public Book updateBook(@PathVariable @Min(value = 1) long id, @RequestBody @Valid BookRequest bookRequest) {
		
		for(int i=0; i < books.size(); i++) {
			if(books.get(i).getId() == id) {
				Book updateBook = convertToBook(id, bookRequest);
				books.set(i, updateBook);
				return updateBook;
			}
		}
		throw new BookNotFoundException("Book not found - " + id);
	}
	
	@Operation(summary = "Delete book", description = "Remove a book from the list")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable @Min(value = 1) long id) {
		books.removeIf(book -> book.getId() == id);
	}

}
