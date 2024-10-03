package com.cts.pas.consumer.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = true)
public class PropertyMasterRepositoryTest {
	
	@Autowired
	private PropertyMasterRepository propertyMasterRepository;
	
	@Test
	public void testfindIndexValueSuccess() {
		assertNotNull(
				propertyMasterRepository.findIndexValue(8));
	}
	
	@Test
	public void testfindIndexValueFailure() {
		assertNull(
				propertyMasterRepository.findIndexValue(2000));
	}

}
