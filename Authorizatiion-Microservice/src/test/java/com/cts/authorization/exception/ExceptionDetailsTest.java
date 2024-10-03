package com.cts.authorization.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class ExceptionDetailsTest {

	private ExceptionDetails details = new ExceptionDetails("test message",HttpStatus.OK.value(),LocalDateTime.now());
	
	@Test
	void testMessageSetter() {
		details.setMessage("new message");
		assertThat(details.getMessage().equals("new message"));
		
	}
	
	@Test
	void testTimeStampSetter() {
		
		LocalDateTime date = LocalDateTime.now();
		details.setTimestamp(date);
		assertThat(details.getTimestamp().equals(date));
		
	}
	
	
}
