package com.cts.pas.quotes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.pas.quotes.entities.Quotes;
import com.cts.pas.quotes.service.QuoteSevice;

@WebMvcTest(value = QuotesController.class)
public class QuotesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuoteSevice quateSevice;

	private Quotes quote1;
	private Quotes quote2;

	@BeforeEach
	public void setup() {
		quote1 = new Quotes(1, 1, 1, "Property in Transit", "32,000 INR");
		quote2 = new Quotes(2, 1, 1, "Property in Transit", "32,000 INR");
	}

	@Test
	@DisplayName("Test Mock MVC client")
	void testMockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("Test Quate Sevice")
	void testQuateSeviceNotNull() {
		assertThat(quateSevice).isNotNull();
	}

	@Test
	@DisplayName("Test getQuotes() of the Controller")
	void testgetgetQuotesReturnSucess() throws Exception {

		when(quateSevice.getQuotes(anyLong(), anyLong(), anyString())).thenReturn(quote1.getQuote());

		this.mockMvc.perform(get("/getquotesforpolicy/1/1/1")).andExpect(status().isOk());

		verify(quateSevice, times(1)).getQuotes(anyLong(), anyLong(), anyString());

	}

	@Test
	@DisplayName("Test getAllQuotese() of the Controller")
	void testgetgetAllQuotesReturnSucess() throws Exception {

		when(quateSevice.getAllQuotes()).thenReturn((Arrays.asList(quote1, quote2)));

		this.mockMvc.perform(get("/getquotesforpolicy")).andExpect(status().isOk())
		.andExpectAll(jsonPath("$.length()", is(2)));

		verify(quateSevice, times(1)).getAllQuotes();

	}

}
