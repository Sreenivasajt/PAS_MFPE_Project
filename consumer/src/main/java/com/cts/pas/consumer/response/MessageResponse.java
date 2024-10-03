package com.cts.pas.consumer.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageResponse {
	private String message;
	private HttpStatus httpStatus;
	
	public MessageResponse(String message) {
		super();
		this.message = message;
	}
	
	
}
