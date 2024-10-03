package com.cts.pas.policy.exception;

import java.io.IOException;

import java.net.ConnectException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import feign.FeignException;
import feign.RetryableException;

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

	@ExceptionHandler(InApplicablePolicyException.class)
	public ResponseEntity<ExceptionDetails> handleInApplicablePolicyException(InApplicablePolicyException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails();
		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionDetail.setMessage(ex.getMessage());
		exceptionDetail.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(InvalidConsumerPolicyIDException.class)
	public ResponseEntity<ExceptionDetails> handleInvalidConsumerPolicyIDException(
			InvalidConsumerPolicyIDException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails();
		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionDetail.setMessage(ex.getMessage());
		exceptionDetail.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(InvalidPolicyException.class)
	public ResponseEntity<ExceptionDetails> handleInvalidPolicyException(InvalidPolicyException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails();
		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionDetail.setMessage(ex.getMessage());
		exceptionDetail.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(PolicyExistsException.class)
	public ResponseEntity<ExceptionDetails> handlePolicyExistsException(PolicyExistsException ex) {
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
//
//	@ExceptionHandler(ConnectException.class)
//	public ResponseEntity<ExceptionDetails> handleConnectException(ConnectException ex) {
//		ExceptionDetails exceptionDetail = new ExceptionDetails();
//		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		exceptionDetail.setMessage("Connection error!");
//		exceptionDetail.setTimestamp(LocalDateTime.now());
//		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//
//	@ExceptionHandler(IOException.class)
//	public ResponseEntity<ExceptionDetails> handleIOException(IOException ex) {
//		ExceptionDetails exceptionDetail = new ExceptionDetails();
//		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		exceptionDetail.setMessage(ex.getMessage());
//		exceptionDetail.setTimestamp(LocalDateTime.now());
//		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//
//	@ExceptionHandler(RetryableException.class)
//	public ResponseEntity<ExceptionDetails> handleRetryableException(RetryableException ex) {
//		ExceptionDetails exceptionDetail = new ExceptionDetails();
//		exceptionDetail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		exceptionDetail.setMessage("Connection error!");
//		exceptionDetail.setTimestamp(LocalDateTime.now());
//		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}

}