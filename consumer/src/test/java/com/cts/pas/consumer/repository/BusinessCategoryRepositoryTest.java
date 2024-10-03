package com.cts.pas.consumer.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = true)
public class BusinessCategoryRepositoryTest {
	
	@Autowired
	private BusinessCategoryRepository businessCategoryRepository;
	
	@Test
	public void testfindByBusinessCategoryAndBusinessTypeSuccess() {
		assertNotNull(
				businessCategoryRepository.findByBusinessCategoryAndBusinessType("Business Services", "Consultant"));
	}
	
	@Test
	public void testfindByBusinessCategoryAndBusinessTypeFailure() {
		assertNull(
				businessCategoryRepository.findByBusinessCategoryAndBusinessType("ABC", "DEF"));
	}

}
