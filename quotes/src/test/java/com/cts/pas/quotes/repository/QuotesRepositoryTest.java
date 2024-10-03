package com.cts.pas.quotes.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.pas.quotes.repositary.QuotesRepository;

@DataJpaTest(showSql = true)
public class QuotesRepositoryTest {

	@Autowired
	private QuotesRepository quotesRepository;

	@Test
	public void testFindAll() {
		assertThat(quotesRepository.findAll()).hasSize(19);
	}

	@Test
	public void testFindByBusinessValueAndPropertyValueAndPropertyTypeSuccess() {
		assertNotNull(
				quotesRepository.findByBusinessValueAndPropertyValueAndPropertyType(8L, 8L, "Property in Transit"));
	}

	@Test
	public void testFindByBusinessValueAndPropertyValueAndPropertyTypeFailure() {
		assertNull(quotesRepository.findByBusinessValueAndPropertyValueAndPropertyType(99L, 8L, "Property in Transit"));
	}
}
