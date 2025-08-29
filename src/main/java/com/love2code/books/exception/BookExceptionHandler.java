package com.love2code.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exception) {
		
		BookErrorResponse bookErrorResponse = new BookErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				exception.getMessage(),
				System.currentTimeMillis()
				);
		
		return new ResponseEntity<>(bookErrorResponse, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<BookErrorResponse> handleException(Exception exception) {
		
		BookErrorResponse bookErrorResponse = new BookErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				"Bad Request",
				System.currentTimeMillis()
				);
		
		return new ResponseEntity<>(bookErrorResponse, HttpStatus.NOT_FOUND);
		
	}

}
