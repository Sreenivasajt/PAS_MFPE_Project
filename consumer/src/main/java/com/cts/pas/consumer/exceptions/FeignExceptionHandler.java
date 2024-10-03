package com.cts.pas.consumer.exceptions;

import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class FeignExceptionHandler {

	private ExceptionDetails getExceptionDetails(String data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ExceptionDetails details = objectMapper.readValue(data, ExceptionDetails.class);
		return details;
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ExceptionDetails> handleFeignStatusException(FeignException ex)
			throws JsonMappingException, JsonProcessingException {
		ExceptionDetails exceptionDetail = getExceptionDetails(ex.contentUTF8());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
	}

}