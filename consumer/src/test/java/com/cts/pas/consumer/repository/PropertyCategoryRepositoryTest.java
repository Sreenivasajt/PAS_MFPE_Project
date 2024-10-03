package com.cts.pas.consumer.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = true)
public class PropertyCategoryRepositoryTest {
	
	@Autowired
	private PropertyCategoryRepository propertyCategoryRepository;
	
	@Test
	public void testfindByPropertyTypeAndBuildingTypeSuccess() {
		assertNotNull(
				propertyCategoryRepository.findByPropertyTypeAndBuildingType("Property in Transit", "Rented"));
	}
	
	@Test
	public void testfindByPropertyTypeAndBuildingTypeFailure() {
		assertNull(
				propertyCategoryRepository.findByPropertyTypeAndBuildingType("ABC", "DEF"));
	}

}
