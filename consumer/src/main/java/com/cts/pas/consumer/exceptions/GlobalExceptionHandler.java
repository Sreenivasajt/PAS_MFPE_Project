package com.cts.pas.consumer.exceptions;

import java.time.LocalDateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetails> handleGeneralException(Exception ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails();
		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionDetail.setMessage(ex.getMessage());
		exceptionDetail.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(BusinessNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleBusinessNotFoundException(BusinessNotFoundException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails();
		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionDetail.setMessage(ex.getMessage());
		exceptionDetail.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(ConsumerNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleConsumerNotFoundException(ConsumerNotFoundException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails();
		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionDetail.setMessage(ex.getMessage());
		exceptionDetail.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(PropertyIdNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handlePropertyIdNotFoundException(PropertyIdNotFoundException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails();
		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionDetail.setMessage(ex.getMessage());
		exceptionDetail.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ExceptionDetails> handleAuthorizationException(AuthorizationException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails();
		exceptionDetail.setCode(HttpStatus.UNAUTHORIZED.value());
		exceptionDetail.setMessage(ex.getMessage());
		exceptionDetail.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.UNAUTHORIZED);

	}

}