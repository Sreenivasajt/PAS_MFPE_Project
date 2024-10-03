package com.cts.pas.quotes.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.pas.quotes.entities.Quotes;
import com.cts.pas.quotes.repositary.QuotesRepository;

@SpringBootTest
public class QuotesServiceTest {

	@MockBean
	private QuotesRepository quotesRepository;

	@InjectMocks
	private QuoteSevice quoteSevice;

	private Quotes quote1;
	private Quotes quote2;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		quote1 = new Quotes(2, 1, 1, "Property in Transit", "32,000 INR");
		quote2 = new Quotes(2, 1, 1, "Property in Transit", "32,000 INR");
	}

	@Test
	@DisplayName("Test Mock Quotes Repository")
	void testQuotesRepositoryNotNull() {
		assertThat(quotesRepository).isNotNull();
	}

	@Test
	@DisplayName("Test getAllQuoates() return success")
	public void testGetAllQuotes() {
		when(quotesRepository.findAll()).thenReturn(Arrays.asList(quote1, quote2));
		assertThat(quoteSevice.getAllQuotes()).isNotNull();
	}

	@Test
	@DisplayName("Test getQuotes return success")
	public void testGetQuotesSuccess() {
		when(quotesRepository.findByBusinessValueAndPropertyValueAndPropertyType(anyLong(), anyLong(), anyString()))
				.thenReturn(quote1);
		assertThat(quoteSevice.getQuotes(anyLong(), anyLong(), anyString())).isNotNull();
	}

	@Test
	@DisplayName("Test getQuotes return failure")
	public void testGetQuotesFailure() {
		when(quotesRepository.findByBusinessValueAndPropertyValueAndPropertyType(anyLong(), anyLong(), anyString()))
				.thenReturn(null);
		assertThat(quoteSevice.getQuotes(anyLong(), anyLong(), anyString()))
				.contains("No Quotes, Contact Insurance Provider");
	}

}
